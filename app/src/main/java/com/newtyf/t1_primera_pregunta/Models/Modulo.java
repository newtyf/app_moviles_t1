package com.newtyf.t1_primera_pregunta.Models;

import java.util.ArrayList;
import java.util.List;

public class Modulo {
    private int id;
    private String nombre;
    private List<Departamento> departamentos;

    public Modulo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.departamentos = new ArrayList<>();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<Departamento> getDepartamentos() { return departamentos; }
    public void setDepartamentos(List<Departamento> departamentos) { this.departamentos = departamentos; }

    public void agregarDepartamento(Departamento d) { departamentos.add(d); }

    @Override
    public String toString() { return id + " - " + nombre; }
}
