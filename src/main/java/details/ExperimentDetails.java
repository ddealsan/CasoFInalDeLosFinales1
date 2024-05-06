package details;

import AlmacenamientoPoblaciones.BacteriaPopulation;
import AlmacenamientoPoblaciones.Experimento;
import AlmacenamientoPoblaciones.ExperimentoReader;

import javax.swing.*;
import java.io.File;
import java.awt.*;

public class ExperimentDetails extends JPanel {
    private JPanel detailsPanel;
    private ExperimentoReader experimentoReader; // Añade esta línea

    public ExperimentDetails(File experimentFile) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        add(detailsPanel);
        experimentoReader = new ExperimentoReader(); // Inicializa la instancia de ExperimentoReader
        loadExperimentDetails(experimentFile);
    }

    public ExperimentDetails(File experimentFile, String populationName) {
        this(experimentFile);
        if (populationName != null) {
            loadPopulationDetails(experimentFile, populationName);
        }
    }

    private void loadExperimentDetails(File experimentFile) {
        Experimento experimento = experimentoReader.leerExperimento(experimentFile.getAbsolutePath()); // Usa la instancia para llamar al método
        detailsPanel.add(new JLabel("Nombre del experimento: " + experimento.getNombre()));
        detailsPanel.add(new JLabel("Fecha de inicio: " + experimento.getFechaInicio()));
        detailsPanel.add(new JLabel("Fecha de finalización: " + experimento.getFechaFin()));
    }

    private void loadPopulationDetails(File experimentFile, String populationName) {
        Experimento experimento = experimentoReader.leerExperimento(experimentFile.getAbsolutePath()); // Usa la instancia para llamar al método
        BacteriaPopulation poblacion = null;
        for (int i = 0; i < experimento.getNumeroPoblaciones(); i++) {
            if (experimento.getPoblacion(i).getNombre().equals(populationName)) {
                poblacion = experimento.getPoblacion(i);
                break;
            }
        }

        if (poblacion != null) {
            detailsPanel.add(new JLabel("\n\nDetalles de la población:"));
            detailsPanel.add(new JLabel(poblacion.toString()));
        }
    }
}