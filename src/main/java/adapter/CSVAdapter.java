package adapter;


import java.util.List;

public class CSVAdapter implements ReporteAdapter {

    @Override
    public byte[] exportar(List<String> datos, String titulo) {
        StringBuilder csv = new StringBuilder();
        csv.append(titulo).append("\n");
        csv.append("linea,detalle\n");
        for (int i = 0; i < datos.size(); i++) {
            csv.append(i + 1).append(",").append(datos.get(i)).append("\n");
        }
        return csv.toString().getBytes();
    }
}