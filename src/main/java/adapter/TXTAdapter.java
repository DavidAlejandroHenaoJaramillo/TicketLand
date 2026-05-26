package adapter;

import java.util.List;

public class TXTAdapter implements ReporteAdapter {

    @Override
    public byte[] exportar(List<String> datos, String titulo) {
        StringBuilder txt = new StringBuilder();
        txt.append("HISTORIAL DE COMPRAS\n");
        txt.append(titulo).append("\n\n");
        for (String linea : datos) {
            txt.append(linea).append("\n");
        }
        return txt.toString().getBytes();
    }
}