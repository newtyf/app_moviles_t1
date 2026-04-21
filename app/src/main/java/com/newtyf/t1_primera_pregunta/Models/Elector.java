package com.newtyf.t1_primera_pregunta.Models;

public class Elector {
    private int id;
    private String nombre;
    private String cedula;
    private String password;
    private String departamento;
    private String modulo;
    private Integer votoCandidatoId;

    public Elector(int id, String nombre, String cedula, String password, String departamento, String modulo) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.password = password;
        this.departamento = departamento;
        this.modulo = modulo;
        this.votoCandidatoId = null;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getModulo() { return modulo; }
    public void setModulo(String modulo) { this.modulo = modulo; }

    public Integer getVotoCandidatoId() { return votoCandidatoId; }
    public void setVotoCandidatoId(Integer votoCandidatoId) { this.votoCandidatoId = votoCandidatoId; }

    public boolean haVotado() { return votoCandidatoId != null; }

    @Override
    public String toString() { return id + " - " + nombre + " (" + cedula + ")"; }
}
