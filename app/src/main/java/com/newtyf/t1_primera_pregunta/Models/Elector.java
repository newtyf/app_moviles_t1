package com.newtyf.t1_primera_pregunta.Models;

public class Elector {
    private int id;
    private String nombre;
    private String cedula;
    private String password;
    private int departamentoId;
    private int moduloId;
    private Integer votoCandidatoId;

    public Elector(int id, String nombre, String cedula, String password, int departamentoId, int moduloId) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.password = password;
        this.departamentoId = departamentoId;
        this.moduloId = moduloId;
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

    public int getDepartamentoId() { return departamentoId; }
    public void setDepartamentoId(int departamentoId) { this.departamentoId = departamentoId; }

    public int getModuloId() { return moduloId; }
    public void setModuloId(int moduloId) { this.moduloId = moduloId; }

    public Integer getVotoCandidatoId() { return votoCandidatoId; }
    public void setVotoCandidatoId(Integer votoCandidatoId) { this.votoCandidatoId = votoCandidatoId; }

    public boolean haVotado() { return votoCandidatoId != null; }

    @Override
    public String toString() { return id + " - " + nombre + " (" + cedula + ")"; }
}
