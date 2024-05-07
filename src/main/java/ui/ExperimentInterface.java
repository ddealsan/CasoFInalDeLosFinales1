package ui;

import AlmacenamientoPoblaciones.BacteriaPopulation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.ParseException;

public class ExperimentInterface extends JFrame {
    private JList<String> experimentList;
    private JPanel populationPanel;
    private JButton addExperimentButton;
    private JButton addPopulationButton;
    private JButton deletePopulationButton;
    private JButton showExperimentDetailsButton;
    private JButton deleteExperimentButton;

    private JList<String> populationList;


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

        populationPanel = new JPanel();
        populationPanel.setLayout(new BoxLayout(populationPanel, BoxLayout.PAGE_AXIS));
        populationList = new JList<>();



        // Add experiment list
        File experimentFolder = new File("src/main/resources/experimentos");
        String[] experimentFiles = experimentFolder.list();
        experimentList = new JList<>(experimentFiles);
        experimentList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Clear the population panel and list
                populationPanel.removeAll();
                DefaultListModel<String> populationListModel = new DefaultListModel<>();
                populationList.setModel(populationListModel);

                // Get the selected experiment file
                File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());

                try (Scanner scanner = new Scanner(selectedExperimentFile)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.startsWith("Nombre de la poblacion: ")) {
                            // Add the population name to the population list
                            String populationName = line.substring("Nombre de la poblacion: ".length());
                            populationListModel.addElement(populationName);
                        }
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                // Create a JScrollPane for the population list
                JScrollPane populationScrollPane = new JScrollPane(populationList);

                // Set the maximum size of the JScrollPane to the maximum possible size
                populationScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

                // Add the JScrollPane to the population panel
                populationPanel.add(populationScrollPane);

                // Refresh the population panel
                populationPanel.revalidate();
                populationPanel.repaint();
            }
        });
        JScrollPane experimentScrollPane = new JScrollPane(experimentList);
        splitPane.setLeftComponent(experimentScrollPane);

        // Add population list
        populationList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Perform an action when a population is selected
                // For example, you can display the details of the selected population
            }
        });
        JScrollPane populationScrollPane = new JScrollPane(populationPanel);
        splitPane.setRightComponent(populationScrollPane);





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
            String populationName = JOptionPane.showInputDialog("Introduce el nombre de la población:");
            int bacteriaCount = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número de bacterias:"));
            int initialFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida inicial (primer día):"));
            int highestFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida más alta:"));
            int finalFood = Integer.parseInt(JOptionPane.showInputDialog("Introduce la comida final (último día):"));
            int temperature = Integer.parseInt(JOptionPane.showInputDialog("Introduce la temperatura a la que serán sometidas:"));
            String[] lightLevels = {"Alta", "Media", "Baja"};
            String lightLevel = (String) JOptionPane.showInputDialog(null, "Selecciona el tipo de luminosidad:", "Luminosidad", JOptionPane.QUESTION_MESSAGE, null, lightLevels, lightLevels[0]);

            BacteriaPopulation population = new BacteriaPopulation(populationName, bacteriaCount, initialFood, highestFood, finalFood, temperature, lightLevel);

            // Append the population data to the selected experiment file
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());
            try (FileWriter writer = new FileWriter(selectedExperimentFile, true)) {
                writer.write(population.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Refresh the population panel
            populationPanel.removeAll();
            populationPanel.add(new JLabel(population.toString()));
            populationPanel.revalidate();
            populationPanel.repaint();
        });
        buttonPanel.add(addPopulationButton);
        deletePopulationButton = new JButton("Borrar población");
        deletePopulationButton.addActionListener(e -> {
            // Get the selected experiment file
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());

            // Get the selected population
            String selectedPopulation = populationList.getSelectedValue();

            try {
                // Read the lines of the experiment file
                List<String> lines = Files.readAllLines(selectedExperimentFile.toPath(), StandardCharsets.UTF_8);

                // Create a new list to hold the updated lines
                List<String> updatedLines = new ArrayList<>();

                // Iterate over the lines
                for (String line : lines) {
                    // If the line does not contain the selected population, add it to the updated lines
                    if (!line.contains(selectedPopulation)) {
                        updatedLines.add(line);
                    }
                }

                // Rewrite the experiment file with the updated lines
                Files.write(selectedExperimentFile.toPath(), updatedLines, StandardCharsets.UTF_8);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Refresh the population panel
            populationPanel.removeAll();
            populationPanel.revalidate();
            populationPanel.repaint();
        });
        buttonPanel.add(deletePopulationButton);
        showExperimentDetailsButton = new JButton("Mostrar detalles de experimento");
        showExperimentDetailsButton.addActionListener(e -> {
            // Get the selected experiment file
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());

            // Get the selected population
            String selectedPopulation = populationList.getSelectedValue();

            try {
                // Read the lines of the experiment file
                List<String> lines = Files.readAllLines(selectedExperimentFile.toPath(), StandardCharsets.UTF_8);

                // Variables to hold the population details
                int initialFood = 0, highestFood = 0, finalFood = 0, experimentDays = 0;
                String populationDetails = "";
                String classDetails = "";
                String experimentDuration = "";
                String experimentDetails = "";

                // Iterate over the lines
                for (String line : lines) {
                    // If the line contains the selected population, get the details
                    if (line.contains(selectedPopulation)) {
                        populationDetails += line + "\n";
                        if (line.startsWith("Comida inicial (primer día): ")) {
                            initialFood = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                        } else if (line.startsWith("Comida mas alta: ")) {
                            highestFood = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                        } else if (line.startsWith("Comida final (ultimo día): ")) {
                            finalFood = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                        }
                    } else if (line.startsWith("Fecha de inicio: ")) {
                        String startDate = line.substring("Fecha de inicio: ".length());
                        String endDate = lines.get(lines.indexOf(line) + 1).substring("Fecha de finalización: ".length());
                        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                        Date start = format.parse(startDate);
                        Date end = format.parse(endDate);
                        long difference = end.getTime() - start.getTime();
                        experimentDays = (int) TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                        experimentDuration = "Duración del experimento: " + experimentDays + " días\n";
                    } else if (line.startsWith("Clase: ")) {
                        classDetails = line + "\n";
                    } else if (line.startsWith("Temperatura a la que serán sometidas: ") || line.startsWith("Tipo de luminosidad: ") || line.startsWith("Numero de bacterias: ")) {
                        experimentDetails += line + "\n";
                    }
                }

                // Calculate the food for each day
                int midExperimentDay = experimentDays / 2 > 15 ? 15 : experimentDays / 2;
                double foodIncreasePerDay = (double) (highestFood - initialFood) / midExperimentDay;
                double foodDecreasePerDay = (double) (highestFood - finalFood) / (experimentDays - midExperimentDay);
                String dailyFood = "Comida diaria: \n";
                for (int i = 1; i <= experimentDays; i++) {
                    if (i <= midExperimentDay) {
                        dailyFood += "Día " + i + ": " + (initialFood + foodIncreasePerDay * (i - 1)) + "\n";
                    } else {
                        dailyFood += "Día " + i + ": " + (highestFood - foodDecreasePerDay * (i - midExperimentDay - 1)) + "\n";
                    }
                }

                // Create a new JFrame to display the population details
                JFrame detailsFrame = new JFrame("Detalles de la población");
                detailsFrame.setSize(500, 500);
                detailsFrame.setLocationRelativeTo(null);
                JTextArea detailsArea = new JTextArea(classDetails + populationDetails + experimentDuration + experimentDetails + dailyFood);
                detailsArea.setEditable(false);
                detailsFrame.add(new JScrollPane(detailsArea));
                detailsFrame.setVisible(true);
            } catch (IOException | ParseException exception) {
                exception.printStackTrace();
            }
        });
        buttonPanel.add(showExperimentDetailsButton);
        deleteExperimentButton = new JButton("Borrar experimento");
        deleteExperimentButton.addActionListener(e -> {
            // Get the selected experiment file
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());

            // Delete the experiment file
            if (selectedExperimentFile.delete()) {
                System.out.println("Experimento borrado exitosamente.");
            } else {
                System.out.println("Error al borrar el experimento.");
            }

            // Refresh the experiment list
            experimentList.setListData(experimentFolder.list());
        });
        buttonPanel.add(deleteExperimentButton);
        add(buttonPanel, BorderLayout.NORTH); // Changed from SOUTH to NORTH

        // Make the frame visible
        setVisible(true);
    }
}