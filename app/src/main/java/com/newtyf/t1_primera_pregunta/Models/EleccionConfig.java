package com.newtyf.t1_primera_pregunta.Models;

public class EleccionConfig {
    private String fecha;       // formato: dd/MM/yyyy
    private String horaInicio;  // formato: HH:mm
    private String horaFin;     // formato: HH:mm

    public EleccionConfig(String fecha, String horaInicio, String horaFin) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }
}
