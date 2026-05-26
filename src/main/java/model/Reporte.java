package model;

import java.time.LocalDate;

// RF-046: entidad de reporte operativo
public class Reporte {

    private TipoReporte tipo;
    private LocalDate inicio;
    private LocalDate fin;
    private String titulo;

    public Reporte(TipoReporte tipo, LocalDate inicio, LocalDate fin) {
        this.tipo = tipo;
        this.inicio = inicio;
        this.fin = fin;
        this.titulo = tipo.name() + " | " + inicio + " a " + fin;
    }

    public byte[] generarPDF()  { return new byte[0]; } // delegar a ReporteService
    public byte[] generarCSV()  { return new byte[0]; }

    public TipoReporte getTipo() { return tipo; }
    public LocalDate getInicio() { return inicio; }
    public LocalDate getFin()    { return fin; }
    public String getTitulo()    { return titulo; }
}