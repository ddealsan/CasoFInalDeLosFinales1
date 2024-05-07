package Poblacion;


import AlmacenamientoPoblaciones.BacteriaPopulation;
import AlmacenamientoPoblaciones.Experimento;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class ExperimentoManager {
    private Experimento experimentoActual;

    public void agregarExperimento(String nombre, String fechaInicio, String fechaFin) {
        this.experimentoActual = new Experimento(nombre, fechaInicio, fechaFin);
    }

    public void agregarPoblacion(String nombre, int comidaInicial, int comidaMaxima, int comidaFinal, int temperatura, String luminosidad) {
        BacteriaPopulation nuevaPoblacion = new BacteriaPopulation(nombre, 0, comidaInicial, comidaMaxima, comidaFinal, temperatura, luminosidad);
        this.experimentoActual.addBacteriaPopulation(nuevaPoblacion);
    }

    public String calcularComida() {
        LocalDate fechaInicio = LocalDate.parse(this.experimentoActual.getFechaInicio());
        LocalDate fechaFin = LocalDate.parse(this.experimentoActual.getFechaFin());
        long duracionExperimento = ChronoUnit.DAYS.between(fechaInicio, fechaFin);

        StringBuilder resultado = new StringBuilder();
        for (BacteriaPopulation poblacion : this.experimentoActual.getPoblaciones()) {
            int comidaInicial = poblacion.getInitialFood();
            int comidaMaxima = poblacion.getHighestFood();
            int comidaFinal = poblacion.getFinalFood();

            int incrementoComida = (comidaMaxima - comidaInicial) / ((int) duracionExperimento / 2);
            int decrementoComida = (comidaMaxima - comidaFinal) / ((int) duracionExperimento / 2);

            resultado.append("Comida para la poblacion ").append(poblacion.getNombre()).append(":\n");
            for (int i = 0; i < duracionExperimento; i++) {
                int comidaDia;
                if (i < duracionExperimento / 2) {
                    comidaDia = comidaInicial + incrementoComida * i;
                } else {
                    comidaDia = comidaMaxima - decrementoComida * (i - (int) duracionExperimento / 2);
                }
                resultado.append("Dia ").append(i + 1).append(": ").append(comidaDia).append("\n");
            }
        }

        return resultado.toString();
    }
}