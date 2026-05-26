package adapter;

import java.util.List;

// Clase "adaptada" que genera CSV en bruto (simula librería externa)
public class CSVBoxGenerator {

    public byte[] crearCSV(List<String> datos, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append(titulo).append("\n");
        for (String linea : datos) {
            sb.append(linea).append("\n");
        }
        return sb.toString().getBytes();
    }
}