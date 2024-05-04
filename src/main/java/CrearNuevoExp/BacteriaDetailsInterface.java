// BacteriaDetailsInterface.java
package CrearNuevoExp;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BacteriaDetailsInterface extends JFrame {
    private List<JTextField> bacteriaNameFields = new ArrayList<>();
    private List<JTextField> initialFoodFields = new ArrayList<>();
    private List<JTextField> finalFoodFields = new ArrayList<>();
    private int bacteriaCount;

    public BacteriaDetailsInterface(int bacteriaCount) {
        this.bacteriaCount = bacteriaCount;
        setTitle("Detalles de las bacterias");
        setPreferredSize(new Dimension( 800, 100 + 100 * bacteriaCount)); // Establecer el tamaño preferido de la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        for (int i = 0; i < bacteriaCount; i++) {
            JPanel row = new JPanel();
            row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));

            row.add(new JLabel("Nombre de la población:"));
            JTextField bacteriaNameField = new JTextField();
            bacteriaNameField.setMinimumSize(new Dimension(100, 30)); // Aumentar el tamaño del campo de entrada
            bacteriaNameFields.add(bacteriaNameField);
            row.add(bacteriaNameField);

            row.add(new JLabel("Comida inicial:"));
            JTextField initialFoodField = new JTextField();
            initialFoodField.setMinimumSize(new Dimension(100, 30)); // Aumentar el tamaño del campo de entrada
            initialFoodFields.add(initialFoodField);
            row.add(initialFoodField);

            row.add(new JLabel("Comida final:"));
            JTextField finalFoodField = new JTextField();
            finalFoodField.setMinimumSize(new Dimension(100, 30)); // Aumentar el tamaño del campo de entrada
            finalFoodFields.add(finalFoodField);
            row.add(finalFoodField);

            add(row);
        }

        JButton saveButton = new JButton("Guardar");
        saveButton.addActionListener(e -> saveBacteriaDetails());
        add(saveButton);

        pack();
        setVisible(true);
    }

    private void saveBacteriaDetails() {
        String data = "";

        for (int i = 0; i < bacteriaCount; i++) {
            data += "Nombre de la población: " + bacteriaNameFields.get(i).getText() + "\n" +
                    "Comida inicial: " + initialFoodFields.get(i).getText() + "\n" +
                    "Comida final: " + finalFoodFields.get(i).getText() + "\n";
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/experimentDetails.txt", true))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JOptionPane.showMessageDialog(this, "Los detalles de las bacterias se han guardado correctamente.");
        dispose();
    }
}