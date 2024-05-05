package AlmacenamientoPoblaciones;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BacteriaPopulation {
    private String name;
    private int bacteriaCount;
    private int initialFood;
    private int highestFood;
    private int finalFood;
    private int temperature;
    private String lightLevel;

    // En lugar de JPanel, usa JList para populationPanel
    private JList<String> populationList;

    public BacteriaPopulation(String name, int bacteriaCount, int initialFood, int highestFood, int finalFood, int temperature, String lightLevel) {
        this.name = name;
        this.bacteriaCount = bacteriaCount;
        this.initialFood = initialFood;
        this.highestFood = highestFood;
        this.finalFood = finalFood;
        this.temperature = temperature;
        this.lightLevel = lightLevel;

        // En la sección donde configuras el panel de población
        populationList = new JList<>();
        JSplitPane splitPane = new JSplitPane();
        splitPane.setRightComponent(new JScrollPane(populationList));

        // En el ActionListener del botón addPopulationButton
        JButton addPopulationButton = new JButton();
        addPopulationButton.addActionListener(e -> {
            // ... código para recoger y validar la entrada del usuario ...

            BacteriaPopulation population = new BacteriaPopulation(name, bacteriaCount, initialFood, highestFood, finalFood, temperature, lightLevel);

            // Append the population data to the selected experiment file
            File experimentFolder = new File("path_to_your_experiment_folder");
            JList<String> experimentList = new JList<>();
            File selectedExperimentFile = new File(experimentFolder, experimentList.getSelectedValue());
            try (FileWriter writer = new FileWriter(selectedExperimentFile, true)) {
                writer.write(population.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Actualiza la lista de poblaciones con el nombre de la nueva población
            DefaultListModel<String> model = (DefaultListModel<String>) populationList.getModel();
            model.addElement(name);
        });
    }

    @Override
    public String toString() {
        return "Nombre de la poblacion: " + name + "\n" +
                "Numero de bacterias: " + bacteriaCount + "\n" +
                "Comida inicial (primer día): " + initialFood + "\n" +
                "Comida mas alta: " + highestFood + "\n" +
                "Comida final (ultimo día): " + finalFood + "\n" +
                "Temperatura a la que serán sometidas: " + temperature + "\n" +
                "Tipo de luminosidad: " + lightLevel + "\n";
    }
}