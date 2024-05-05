package ui;

import details.ExperimentDetails;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ExperimentInterface extends JFrame {
    private JList<String> experimentList;
    private JList<String> populationList;
    private JPanel detailsPanel;
    private JButton addExperimentButton;
    private JButton addPopulationButton;
    private JButton deletePopulationButton;
    private JButton showExperimentDetailsButton;
    private JButton deleteExperimentButton;
    private File experimentFolder;

    public ExperimentInterface() {
        setTitle("Interfaz de experimentos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(200);
        splitPane.setEnabled(false);
        getContentPane().add(splitPane, BorderLayout.CENTER);

        experimentFolder = new File("src/main/resources/experimentos");
        String[] experimentFiles = experimentFolder.list();
        experimentList = new JList<>(experimentFiles);
        JScrollPane experimentScrollPane = new JScrollPane(experimentList);
        splitPane.setLeftComponent(experimentScrollPane);

        JSplitPane rightSplitPane = new JSplitPane();
        rightSplitPane.setDividerSize(1);
        rightSplitPane.setDividerLocation(200);
        rightSplitPane.setEnabled(false);
        splitPane.setRightComponent(rightSplitPane);

        populationList = new JList<>();
        rightSplitPane.setLeftComponent(new JScrollPane(populationList));

        detailsPanel = new JPanel();
        rightSplitPane.setRightComponent(detailsPanel);

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

        showExperimentDetailsButton.addActionListener(e -> {
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());
            String selectedPopulation = populationList.getSelectedValue();
            detailsPanel.removeAll();
            if (selectedPopulation != null) {
                detailsPanel.add(new ExperimentDetails(selectedExperimentFile, selectedPopulation));
            } else {
                detailsPanel.add(new ExperimentDetails(selectedExperimentFile));
            }
            detailsPanel.revalidate();
            detailsPanel.repaint();
        });

        deleteExperimentButton.addActionListener(e -> {
            String selectedExperiment = experimentList.getSelectedValue();
            if (selectedExperiment != null) {
                File selectedExperimentFile = new File(experimentFolder, selectedExperiment);
                selectedExperimentFile.delete();
                experimentList.setListData(experimentFolder.list());
                populationList.setModel(new DefaultListModel<>());
            }
        });

        deletePopulationButton.addActionListener(e -> {
            String selectedExperiment = experimentList.getSelectedValue();
            String selectedPopulation = populationList.getSelectedValue();
            if (selectedExperiment != null && selectedPopulation != null) {
                File selectedExperimentFile = new File(experimentFolder, selectedExperiment);
                try {
                    List<String> lines = Files.readAllLines(selectedExperimentFile.toPath());
                    List<String> modified = lines.stream()
                            .filter(line -> !line.contains("Nombre de la poblacion: " + selectedPopulation))
                            .collect(Collectors.toList());
                    Files.write(selectedExperimentFile.toPath(), modified);
                    loadPopulationNames(selectedExperimentFile);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

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