package com.m.d.f.miagenda.modelos;

public class Meta {

    private int id;
    private String titulo;
    private String descripcion;
    private float progreso;    // REAL (0–100)
    private boolean completada;    // 0 = no, 1 = sí

    public Meta() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getProgreso() { return progreso; }
    public void setProgreso(float progreso) { this.progreso = progreso; }

    public boolean getCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }
}
