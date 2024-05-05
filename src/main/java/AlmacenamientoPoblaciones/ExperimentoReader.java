package AlmacenamientoPoblaciones;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExperimentoReader {

    public static Experimento leerExperimento(String archivo) {
        Experimento experimento = new Experimento("","","");
        try {
            FileInputStream fileIn = new FileInputStream(archivo);
            ObjectInputStream lector = new ObjectInputStream(fileIn);

            experimento = (Experimento) lector.readObject();
            lector.close();
        } catch (IOException | ClassNotFoundException e) {
            // No hacer nada, simplemente se llegó al final del archivo
        }
        return experimento;
    }
}
