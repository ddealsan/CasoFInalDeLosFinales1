package AlmacenamientoPoblaciones;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ExperimentoReader {
    public Experimento leerExperimento(String filename) {
        Experimento experimento = null;
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            experimento = (Experimento) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.out.println("Experimento class not found");
            c.printStackTrace();
        }
        return experimento;
    }
}