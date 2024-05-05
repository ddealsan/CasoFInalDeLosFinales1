package ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExperimentInterface extends JFrame {
    private JList<String> experimentList;
    private JPanel populationPanel;
    private JButton addExperimentButton;
    private JButton addPopulationButton;
    private JButton deletePopulationButton;
    private JButton showExperimentDetailsButton;
    private JButton deleteExperimentButton;

    public ExperimentInterface() {
        // Set up the frame
        setTitle("Interfaz de experimentos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up the layout
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(700); // Adjust the divider location
        splitPane.setEnabled(false);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        // Add experiment list
        File experimentFolder = new File("src/main/resources/experimentos");
        String[] experimentFiles = experimentFolder.list();
        experimentList = new JList<>(experimentFiles);
        JScrollPane experimentScrollPane = new JScrollPane(experimentList);
        splitPane.setLeftComponent(experimentScrollPane);

        // Add population panel
        populationPanel = new JPanel();
        splitPane.setRightComponent(populationPanel);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        addExperimentButton = new JButton("Agregar experimento");
        addExperimentButton.addActionListener(e -> {
            String experimentName = JOptionPane.showInputDialog("Introduce el nombre del experimento:");
            String startDate = JOptionPane.showInputDialog("Introduce la fecha de inicio (dd/mm/yyyy):");
            String endDate = JOptionPane.showInputDialog("Introduce la fecha de finalización (dd/mm/yyyy):");
            File newExperimentFile = new File(experimentFolder, experimentName + ".txt");
            try (FileWriter writer = new FileWriter(newExperimentFile)) {
                writer.write("Nombre del experimento: " + experimentName + "\n");
                writer.write("Fecha de inicio: " + startDate + "\n");
                writer.write("Fecha de finalización: " + endDate + "\n");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            // Refresh the experiment list
            experimentList.setListData(experimentFolder.list());
        });
        buttonPanel.add(addExperimentButton);
        addPopulationButton = new JButton("Agregar población");
        addPopulationButton.addActionListener(e -> {
            // Add the action for adding a population
        });
        buttonPanel.add(addPopulationButton);
        deletePopulationButton = new JButton("Borrar población");
        deletePopulationButton.addActionListener(e -> {
            // Add the action for deleting a population
        });
        buttonPanel.add(deletePopulationButton);
        showExperimentDetailsButton = new JButton("Mostrar detalles de experimento");
        showExperimentDetailsButton.addActionListener(e -> {
            // Add the action for showing experiment details
        });
        buttonPanel.add(showExperimentDetailsButton);
        deleteExperimentButton = new JButton("Borrar experimento");
        deleteExperimentButton.addActionListener(e -> {
            // Add the action for deleting an experiment
        });
        buttonPanel.add(deleteExperimentButton);
        add(buttonPanel, BorderLayout.NORTH); // Changed from SOUTH to NORTH

        // Make the frame visible
        setVisible(true);
    }
}