package ui;

import AlmacenamientoPoblaciones.BacteriaPopulation;
import AlmacenamientoPoblaciones.Experimento;
import AlmacenamientoPoblaciones.ExperimentoReader;
import AlmacenamientoPoblaciones.ExperimentoWriter;
import details.ExperimentDetails;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
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
    private JPanel populationPanel;
    private ExperimentoWriter escritorExperimentos;
    private ExperimentoReader lectorExperimentos;

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

        escritorExperimentos = new ExperimentoWriter();
        lectorExperimentos = new ExperimentoReader();
        experimentFolder = new File("src/main/resources/experimentos");
        String[] experimentFiles = experimentFolder.list();
        experimentList = new JList<>(experimentFiles);
        JScrollPane experimentScrollPane = new JScrollPane(experimentList);
        splitPane.setLeftComponent(experimentScrollPane);

        experimentList = new JList<>(experimentFiles);
        experimentList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (c instanceof JLabel && value instanceof String) {
                    String filename = (String) value;
                    ((JLabel) c).setText(filename.substring(0, filename.length() - 4));
                }
                return c;
            }
        });

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
        addExperimentButton.addActionListener(e -> {
            String experimentName = JOptionPane.showInputDialog("Introduce el nombre del experimento:");
            String startDate = JOptionPane.showInputDialog("Introduce la fecha de inicio del experimento (formato: dd/mm/yyyy):");
            String endDate = JOptionPane.showInputDialog("Introduce la fecha de fin del experimento (formato: dd/mm/yyyy):");

            Experimento newExperiment = new Experimento(experimentName, startDate, endDate);
            File newExperimentFile = new File(experimentFolder, experimentName + ".txt");
            escritorExperimentos.escribirExperimento(newExperiment, newExperimentFile.getAbsolutePath());

            // Actualizar la lista de experimentos
            experimentList.setListData(experimentFolder.list());
        });
        addPopulationButton = new JButton("Agregar población");
        addPopulationButton.addActionListener(e -> {
            String populationName = null;
            int bacteriaCount = 0;
            int initialFood = 0;
            int highestFood = 0;
            int finalFood = 0;
            int temperature = 0;
            String lightLevel = null;
            boolean validInput = false;

            do {
                try {
                    populationName = JOptionPane.showInputDialog("Introduce el nombre de la población:");
                    bacteriaCount = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número de bacterias:"));
                    initialFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida inicial (primer día):"));
                    highestFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida más alta:"));
                    finalFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida final (último día):"));
                    temperature = Integer.parseInt(JOptionPane.showInputDialog("Introduce la temperatura a la que serán sometidas:"));
                    String[] lightLevels = {"Alta", "Media", "Baja"};
                    lightLevel = (String) JOptionPane.showInputDialog(null, "Selecciona el tipo de luminosidad:", "Luminosidad", JOptionPane.QUESTION_MESSAGE, null, lightLevels, lightLevels[0]);
                    validInput = true;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Entrada inválida. Por favor, introduce un número entero.");
                }
            } while (!validInput);

            BacteriaPopulation population = new BacteriaPopulation(populationName, bacteriaCount, initialFood, highestFood, finalFood, temperature, lightLevel);

            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());
            Experimento experimento = lectorExperimentos.leerExperimento(selectedExperimentFile.getAbsolutePath());
            experimento.addBacteriaPopulation(population);
            escritorExperimentos.escribirExperimento(experimento, selectedExperimentFile.getAbsolutePath());

            // Actualiza la lista de poblaciones en la interfaz de usuario
            loadPopulationNames(selectedExperimentFile);
        });
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
                detailsPanel.removeAll();
                detailsPanel.revalidate();
                detailsPanel.repaint();
            }
        });

        deletePopulationButton.addActionListener(e -> {
            String selectedExperiment = experimentList.getSelectedValue();
            String selectedPopulation = populationList.getSelectedValue();
            if (selectedExperiment != null && selectedPopulation != null) {
                File selectedExperimentFile = new File(experimentFolder, selectedExperiment);
                Experimento experimento = lectorExperimentos.leerExperimento(selectedExperimentFile.getAbsolutePath());
                experimento.getPoblaciones().removeIf(poblacion -> poblacion.getNombre().equals(selectedPopulation));
                escritorExperimentos.escribirExperimento(experimento, selectedExperimentFile.getAbsolutePath());
                loadPopulationNames(selectedExperimentFile);
            }
        });

        setVisible(true);
    }

    private void loadPopulationNames(File experimentFile) {
        Experimento experimento = lectorExperimentos.leerExperimento(experimentFile.getAbsolutePath());
        DefaultListModel<String> model = new DefaultListModel<>();
        for (BacteriaPopulation poblacion : experimento.getPoblaciones()) {
            model.addElement(poblacion.getNombre());
        }
        populationList.setModel(model);
    }
}