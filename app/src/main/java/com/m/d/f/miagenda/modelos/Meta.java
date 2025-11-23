package com.m.d.f.miagenda.modelos;

import java.util.List;

public class Meta {
    private int id;
    private String titulo;
    private String descripcion;
    private double progreso; // 0-100
    private boolean completada;

    public Meta() {}

    public Meta(int id, String titulo, String descripcion, double progreso, Integer eventoId) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.progreso = progreso;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getProgreso() { return progreso; }
    public void setProgreso(double progreso) { this.progreso = progreso; }

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }
}

