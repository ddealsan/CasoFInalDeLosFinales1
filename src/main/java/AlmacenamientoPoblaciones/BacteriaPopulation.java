package AlmacenamientoPoblaciones;


public class BacteriaPopulation {
    private String name;
    private int bacteriaCount;
    private int initialFood;
    private int highestFood;
    private int finalFood;
    private int temperature;
    private String lightLevel;

    private String nombre;
    private int comidaInicial;
    private int comidaMaxima;
    private int comidaFinal;

    public BacteriaPopulation(String name, int bacteriaCount, int initialFood, int highestFood, int finalFood, int temperature, String lightLevel) {
        this.name = name;
        this.bacteriaCount = bacteriaCount;
        this.initialFood = initialFood;
        this.highestFood = highestFood;
        this.finalFood = finalFood;
        this.temperature = temperature;
        this.lightLevel = lightLevel;
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

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getComidaInicial() {
        return this.comidaInicial;
    }

    public void setComidaInicial(int comidaInicial) {
        this.comidaInicial = comidaInicial;
    }

    public int getComidaMaxima() {
        return this.comidaMaxima;
    }

    public void setComidaMaxima(int comidaMaxima) {
        this.comidaMaxima = comidaMaxima;
    }

    public int getComidaFinal() {
        return this.comidaFinal;
    }

    public void setComidaFinal(int comidaFinal) {
        this.comidaFinal = comidaFinal;
    }


    public int getInitialFood() {
        return this.initialFood;
    }

    public int getHighestFood() {
        return this.highestFood;
    }

    public int getFinalFood() {
        return this.finalFood;
    }

}