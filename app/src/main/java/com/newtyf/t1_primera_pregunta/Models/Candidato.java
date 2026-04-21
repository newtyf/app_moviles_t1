package com.newtyf.t1_primera_pregunta.Models;

public class Candidato {
    private int id;
    private String nombre;
    private String propuesta;

    public Candidato(int id, String nombre, String propuesta) {
        this.id = id;
        this.nombre = nombre;
        this.propuesta = propuesta;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPropuesta() { return propuesta; }
    public void setPropuesta(String propuesta) { this.propuesta = propuesta; }

    @Override
    public String toString() { return id + " - " + nombre; }
}
