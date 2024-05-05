package ui;

import AlmacenamientoPoblaciones.BacteriaPopulation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ExperimentInterface extends JFrame {
    private JList<String> experimentList;
    private JList<String> populationList;
    private JButton addExperimentButton;
    private JButton addPopulationButton;
    private JButton deletePopulationButton;
    private JButton showExperimentDetailsButton;
    private JButton deleteExperimentButton;
    private File experimentFolder;

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
        experimentFolder = new File("src/main/resources/experimentos");
        String[] experimentFiles = experimentFolder.list();
        experimentList = new JList<>(experimentFiles);
        JScrollPane experimentScrollPane = new JScrollPane(experimentList);
        splitPane.setLeftComponent(experimentScrollPane);

        // Add population list
        populationList = new JList<>();
        splitPane.setRightComponent(new JScrollPane(populationList));

        // Add buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 5));
        addExperimentButton = new JButton("Agregar experimento");
        addPopulationButton = new JButton("Agregar población");
        deletePopulationButton = new JButton("Borrar población");
        showExperimentDetailsButton = new JButton("Mostrar detalles de experimento");
        deleteExperimentButton = new JButton("Borrar experimento");
        buttonPanel.add(addExperimentButton);
        buttonPanel.add(addPopulationButton);
        buttonPanel.add(deletePopulationButton);
        buttonPanel.add(showExperimentDetailsButton);
        buttonPanel.add(deleteExperimentButton);
        add(buttonPanel, BorderLayout.NORTH);

        experimentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());
                loadPopulationNames(selectedExperimentFile);
            }
        });

        // Make the frame visible
        setVisible(true);
    }

    private void loadPopulationNames(File experimentFile) {
        try (Scanner scanner = new Scanner(experimentFile)) {
            DefaultListModel<String> model = new DefaultListModel<>();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Nombre de la poblacion: ")) {
                    String populationName = line.substring("Nombre de la poblacion: ".length());
                    model.addElement(populationName);
                }
            }
            populationList.setModel(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}