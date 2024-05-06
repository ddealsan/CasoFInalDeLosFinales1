package AlmacenamientoPoblaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Experimento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private List<BacteriaPopulation> bacteriaPopulations;

    public Experimento(String nombre, String fechaInicio, String fechaFin) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.bacteriaPopulations = new ArrayList<>();
    }

    public Experimento(String nombre, String fechaInicio, String fechaFin, List<BacteriaPopulation> bacteriaPopulations) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.bacteriaPopulations = bacteriaPopulations;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void addBacteriaPopulation(BacteriaPopulation bacteriaPopulation) {
        bacteriaPopulations.add(bacteriaPopulation);
    }

    public int getNumeroPoblaciones(){
        return bacteriaPopulations.size();
    }

    public BacteriaPopulation getPoblacion(int index) {
        return bacteriaPopulations.get(index);
    }

    public List<BacteriaPopulation> getPoblaciones() {
        return this.bacteriaPopulations;
    }

    @Override
    public String toString() {
        return "Experimento{" +
                "nombre='" + nombre + '\'' +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", bacteriaPopulations=" + bacteriaPopulations +
                '}';
    }
}