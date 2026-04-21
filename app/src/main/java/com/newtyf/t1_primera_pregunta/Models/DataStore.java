package com.newtyf.t1_primera_pregunta.Models;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private static DataStore instance;

    public final List<Modulo> modulos = new ArrayList<>();
    public final List<Departamento> departamentos = new ArrayList<>();
    public List<Candidato> candidatos = new ArrayList<>();
    public List<Elector> electores = new ArrayList<>();
    public EleccionConfig eleccionConfig = null;

    private int nextCandidatoId = 1;
    private int nextElectorId = 1;

    public static final String ADMIN_USER = "admin";
    public static final String ADMIN_PASS = "admin123";

    private DataStore() {
        String[] nombresModulos = {"Modulo A", "Modulo B", "Modulo C", "Modulo D", "Modulo E"};
        for (int m = 0; m < 5; m++) {
            modulos.add(new Modulo(m + 1, nombresModulos[m]));
        }

        for (int d = 1; d <= 20; d++) {
            departamentos.add(new Departamento(d, "Depto 1" + String.format("%02d", d)));
        }

        candidatos.add(new Candidato(nextCandidatoId++, "Juan Perez", "Seguridad y orden"));
        candidatos.add(new Candidato(nextCandidatoId++, "Maria Lopez", "Mejoras en areas comunes"));

        electores.add(new Elector(nextElectorId++, "Carlos Ruiz", "1234567890", "pass1", 1, 1));
        electores.add(new Elector(nextElectorId++, "Ana Torres", "0987654321", "pass2", 2, 1));
        electores.add(new Elector(nextElectorId++, "Luis Mora", "1122334455", "pass3", 1, 2));
    }

    public static DataStore getInstance() {
        if (instance == null) instance = new DataStore();
        return instance;
    }

    // --- Modulos ---
    public Modulo getModuloPorId(int id) {
        for (int i = 0; i < modulos.size(); i++) {
            if (modulos.get(i).getId() == id) return modulos.get(i);
        }
        return null;
    }

    // --- Departamentos ---
    public Departamento getDepartamentoPorId(int id) {
        for (int i = 0; i < departamentos.size(); i++) {
            if (departamentos.get(i).getId() == id) return departamentos.get(i);
        }
        return null;
    }

    // --- Candidatos ---
    public int nextCandidatoId() { return nextCandidatoId++; }

    public Candidato getCandidatoPorId(int id) {
        for (int i = 0; i < candidatos.size(); i++) {
            if (candidatos.get(i).getId() == id) return candidatos.get(i);
        }
        return null;
    }

    // --- Electores ---
    public int nextElectorId() { return nextElectorId++; }

    public Elector getElectorPorId(int id) {
        for (int i = 0; i < electores.size(); i++) {
            if (electores.get(i).getId() == id) return electores.get(i);
        }
        return null;
    }

    public Elector autenticarElector(String cedula, String password) {
        for (int i = 0; i < electores.size(); i++) {
            Elector e = electores.get(i);
            if (e.getCedula().equals(cedula) && e.getPassword().equals(password)) return e;
        }
        return null;
    }

    // --- Resultados ---
    public int contarVotosCandidato(int candidatoId) {
        int count = 0;
        for (int i = 0; i < electores.size(); i++) {
            Elector e = electores.get(i);
            if (e.getVotoCandidatoId() != null && e.getVotoCandidatoId() == candidatoId) count++;
        }
        return count;
    }

    public int contarVotosEnBlanco() {
        int count = 0;
        for (int i = 0; i < electores.size(); i++) {
            Elector e = electores.get(i);
            if (e.getVotoCandidatoId() != null && e.getVotoCandidatoId() == -1) count++;
        }
        return count;
    }

    public int contarNoVotaron() {
        int count = 0;
        for (int i = 0; i < electores.size(); i++) {
            if (!electores.get(i).haVotado()) count++;
        }
        return count;
    }

    public Candidato getCandidatoGanador() {
        if (candidatos.size() == 0) return null;
        Candidato ganador = null;
        int maxVotos = -1;
        for (int i = 0; i < candidatos.size(); i++) {
            Candidato c = candidatos.get(i);
            int v = contarVotosCandidato(c.getId());
            if (v > maxVotos) {
                maxVotos = v;
                ganador = c;
            }
        }
        return ganador;
    }
}
