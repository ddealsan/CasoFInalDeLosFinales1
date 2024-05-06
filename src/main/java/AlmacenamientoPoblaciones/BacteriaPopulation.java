package AlmacenamientoPoblaciones;

import java.io.Serializable;

public class BacteriaPopulation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int bacteriaCount;
    private int initialFood;
    private int highestFood;
    private int finalFood;
    private int temperature;
    private String lightLevel;

    public BacteriaPopulation(String name, int bacteriaCount, int initialFood, int highestFood, int finalFood, int temperature, String lightLevel) {
        this.name = name;
        this.bacteriaCount = bacteriaCount;
        this.initialFood = initialFood;
        this.highestFood = highestFood;
        this.finalFood = finalFood;
        this.temperature = temperature;
        this.lightLevel = lightLevel;
    }

    public String getNombre() {
        return name;
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