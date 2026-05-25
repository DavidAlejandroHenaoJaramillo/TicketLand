package model;

import adapter.ReporteAdapter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class GeneradorReporte {

    private TicketLand sistema;

    public GeneradorReporte(TicketLand sistema) {
        this.sistema = sistema;
    }

    // RF-046, RF-050: genera reporte usando el adapter recibido (patrón Adapter)
    public String generarConAdapter(ReporteAdapter adapter) {
        return adapter.generarReporte(sistema.getCompras());
    }

    // RF-046: exportar reporte de ventas en CSV
    public void exportarVentasCSV(String rutaArchivo, LocalDate desde, LocalDate hasta) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("Fecha,Usuario,Evento,Total,Estado\n");
            for (Compra c : sistema.getCompras()) {
                if (desde != null && c.getFechaCompra().isBefore(desde)) continue;
                if (hasta != null && c.getFechaCompra().isAfter(hasta)) continue;
                writer.write(
                        c.getFechaCompra() + "," +
                                c.getUsuario().getNombre() + "," +
                                c.getEvento().getNombre() + "," +
                                c.calcularTotal() + "," +
                                c.getEstadoCompra().toString() + "\n"
                );
            }
            System.out.println("CSV exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error exportando CSV: " + e.getMessage());
        }
    }

    // RF-046: exportar reporte de ocupación por zona en CSV
    public void exportarOcupacionCSV(String rutaArchivo) {
        try (FileWriter writer = new FileWriter(rutaArchivo)) {
            writer.write("Evento,Recinto,Zona,Capacidad,Ocupados,Disponibles\n");
            for (Evento e : sistema.getEventos()) {
                if (e.getRecinto() == null) continue;
                for (Zona z : e.getRecinto().getZonas()) {
                    writer.write(
                            e.getNombre() + "," +
                                    e.getRecinto().getNombre() + "," +
                                    z.getTipoZona() + "," +
                                    z.getCapacidad() + "," +
                                    z.calcularOcupacion() + "," +
                                    z.getAsientosDisponibles().size() + "\n"
                    );
                }
            }
            System.out.println("CSV ocupación exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error exportando CSV: " + e.getMessage());
        }
    }

    // RF-046: exportar reporte de ventas en PDF
    public void exportarVentasPDF(String rutaArchivo, LocalDate desde, LocalDate hasta) {
        try (PDDocument documento = new PDDocument()) {
            PDPage pagina = new PDPage();
            documento.addPage(pagina);

            try (PDPageContentStream contenido = new PDPageContentStream(documento, pagina)) {
                PDType1Font fuente = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
                PDType1Font fuenteNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);

                contenido.beginText();
                contenido.setFont(fuente, 16);
                contenido.newLineAtOffset(50, 750);
                contenido.showText("Reporte de Ventas - TicketLand");
                contenido.endText();

                contenido.beginText();
                contenido.setFont(fuenteNormal, 10);
                contenido.newLineAtOffset(50, 730);
                contenido.showText("Generado: " + LocalDate.now());
                contenido.endText();

                contenido.beginText();
                contenido.setFont(fuente, 10);
                contenido.newLineAtOffset(50, 700);
                contenido.showText("Fecha          Usuario               Evento                Total        Estado");
                contenido.endText();

                float y = 680;
                for (Compra c : sistema.getCompras()) {
                    if (desde != null && c.getFechaCompra().isBefore(desde)) continue;
                    if (hasta != null && c.getFechaCompra().isAfter(hasta)) continue;
                    if (y < 50) break;

                    String linea = String.format("%-15s %-20s %-20s %-12s %s",
                            c.getFechaCompra(),
                            truncar(c.getUsuario().getNombre(), 18),
                            truncar(c.getEvento().getNombre(), 18),
                            "$" + (int) c.calcularTotal(),
                            c.getEstadoCompra().toString()
                    );

                    contenido.beginText();
                    contenido.setFont(fuenteNormal, 9);
                    contenido.newLineAtOffset(50, y);
                    contenido.showText(linea);
                    contenido.endText();
                    y -= 20;
                }
            }

            documento.save(rutaArchivo);
            System.out.println("PDF exportado: " + rutaArchivo);
        } catch (IOException e) {
            System.out.println("Error exportando PDF: " + e.getMessage());
        }
    }

    private String truncar(String texto, int maxLen) {
        if (texto == null) return "";
        return texto.length() > maxLen ? texto.substring(0, maxLen) : texto;
    }

    @Override
    public String toString() {
        return "GeneradorReporte - Sistema: " + sistema.getNombre();
    }
}