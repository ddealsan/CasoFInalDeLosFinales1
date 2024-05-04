// NewExperimentInterface.java
package CrearNuevoExp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NewExperimentInterface extends JFrame {
    private JTextField projectNameField;
    private JTextField bacteriaCountField;
    private JTextField temperatureField;
    private JComboBox<String> lightConditionsBox;

    public NewExperimentInterface() {
        setTitle("Nuevo experimento");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Nombre del proyecto:"));
        projectNameField = new JTextField();
        add(projectNameField);

        add(new JLabel("Cantidad de poblaciones de bacterias:"));
        bacteriaCountField = new JTextField();
        add(bacteriaCountField);

        add(new JLabel("Temperatura:"));
        temperatureField = new JTextField();
        add(temperatureField);

        add(new JLabel("Condiciones de luminosidad:"));
        lightConditionsBox = new JComboBox<>(new String[]{"Alta", "Media", "Baja"});
        add(lightConditionsBox);

        JButton continueButton = new JButton("Continuar");
        continueButton.addActionListener(e -> openBacteriaDetailsInterface());
        add(continueButton);

        setVisible(true);
    }

    private void openBacteriaDetailsInterface() {
        int bacteriaCount = Integer.parseInt(bacteriaCountField.getText());
        new BacteriaDetailsInterface(bacteriaCount);
        saveExperimentDetails();
    }

    private void saveExperimentDetails() {
        String data = "Nombre del proyecto: " + projectNameField.getText() + "\n" +
                "Cantidad de poblaciones de bacterias: " + bacteriaCountField.getText() + "\n" +
                "Temperatura: " + temperatureField.getText() + "\n" +
                "Condiciones de luminosidad: " + lightConditionsBox.getSelectedItem().toString() + "\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/experimentDetails.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}