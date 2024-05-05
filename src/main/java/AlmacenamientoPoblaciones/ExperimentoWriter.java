package AlmacenamientoPoblaciones;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class ExperimentoWriter {

    public static void escribirExperimento(String archivo, Experimento experimento){
        try {
            FileOutputStream fileOut = new FileOutputStream(archivo + experimento.getNombre() + ".txt", true);
            ObjectOutputStream escritor = new ObjectOutputStream(fileOut);
            escritor.writeObject(experimento);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
