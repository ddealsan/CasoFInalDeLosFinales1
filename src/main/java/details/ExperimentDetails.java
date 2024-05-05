package details;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.*;

public class ExperimentDetails extends JPanel {
    private JTextArea detailsArea;

    public ExperimentDetails(File experimentFile) {
        setLayout(new BorderLayout());
        detailsArea = new JTextArea();
        add(new JScrollPane(detailsArea), BorderLayout.CENTER);
        loadExperimentDetails(experimentFile);
    }

    public ExperimentDetails(File experimentFile, String populationName) {
        this(experimentFile);
        loadPopulationDetails(populationName);
    }

    private void loadExperimentDetails(File experimentFile) {
        try {
            List<String> lines = Files.readAllLines(experimentFile.toPath());
            String details = lines.stream().collect(Collectors.joining("\n"));
            detailsArea.setText(details);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPopulationDetails(String populationName) {
        String details = detailsArea.getText();
        details += "\n\nDetalles de la población:\n";
        details += "Nombre de la poblacion: " + populationName;
        // Aquí puedes agregar más detalles de la población si los tienes disponibles
        detailsArea.setText(details);
    }
}