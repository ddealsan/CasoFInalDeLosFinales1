package AbrirArchivoConEXP;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ProjectDetailsInterface extends JFrame {
    private JTextArea projectDetailsArea;
    private String projectName;
    private List<JTextField> currentFoodFields = new ArrayList<>();
    private JComboBox<Integer> dayComboBox;

    public ProjectDetailsInterface(String projectName) {
        this.projectName = projectName;
        setTitle("Detalles del proyecto");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        projectDetailsArea = new JTextArea();
        projectDetailsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(projectDetailsArea);
        add(scrollPane, BorderLayout.CENTER);

        JButton editButton = new JButton("Editar");
        editButton.addActionListener(e -> projectDetailsArea.setEditable(true));
        add(editButton, BorderLayout.WEST);

        JButton exitButton = new JButton("Salir");
        exitButton.addActionListener(e -> dispose());
        add(exitButton, BorderLayout.EAST);

        loadProjectDetails();

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 30; i++) {
            dayComboBox.addItem(i);
        }
        dayComboBox.addActionListener(e -> updateCurrentFood());
        bottomPanel.add(dayComboBox);

        for (JTextField currentFoodField : currentFoodFields) {
            bottomPanel.add(currentFoodField);
        }

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadProjectDetails() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("src/main/resources/" + projectName)));
            projectDetailsArea.setText(content);
            // Parse the content to get the initial and final food for each bacteria population
            String[] lines = content.split("\n");
            for (int i = 0; i < lines.length; i += 3) {
                JTextField currentFoodField = new JTextField();
                currentFoodFields.add(currentFoodField);
            }
            updateCurrentFood();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateCurrentFood() {
        int day = (int) dayComboBox.getSelectedItem();
        String[] lines = projectDetailsArea.getText().split("\n");
        for (int i = 0; i < lines.length; i += 3) {
            int initialFood = Integer.parseInt(lines[i + 1].split(": ")[1]);
            int finalFood = Integer.parseInt(lines[i + 2].split(": ")[1]);
            int currentFood = initialFood + (finalFood - initialFood) * day / 30;
            currentFoodFields.get(i / 3).setText(String.valueOf(currentFood));
        }
    }
}