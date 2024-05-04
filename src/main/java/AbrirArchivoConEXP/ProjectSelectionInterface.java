package AbrirArchivoConEXP;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ProjectSelectionInterface extends JFrame {
    private JList<String> projectList;

    public ProjectSelectionInterface() {
        setTitle("Seleccionar un proyecto");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        File folder = new File("src/main/resources/");
        File[] listOfFiles = folder.listFiles();
        DefaultListModel<String> listModel = new DefaultListModel<>();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                listModel.addElement(file.getName());
            }
        }

        projectList = new JList<>(listModel);
        projectList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        projectList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(projectList);
        add(listScroller, BorderLayout.CENTER);

        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(e -> openProjectDetailsInterface());
        add(selectButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void openProjectDetailsInterface() {
        String selectedProject = projectList.getSelectedValue();
        if (selectedProject != null) {
            new ProjectDetailsInterface(selectedProject);
            dispose();
        }
    }
}