package adapter;

import java.util.List;

public interface ReporteAdapter {
    byte[] exportar(List<String> datos, String titulo);
}