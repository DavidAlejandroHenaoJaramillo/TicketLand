package adapter;

import java.util.List;

// Clase "adaptada" que genera PDF en bruto (simula Apache PDFBox)
public class PDFBoxGenerator {

    public byte[] crearPDF(List<String> datos, String titulo) {
        StringBuilder sb = new StringBuilder();
        sb.append("[PDF] ").append(titulo).append("\n");
        for (String linea : datos) {
            sb.append(linea).append("\n");
        }
        return sb.toString().getBytes();
    }
}