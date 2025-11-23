package com.m.d.f.miagenda.modelos;

public class SubMeta {

    private int id;
    private int metaId;
    private String titulo;
    private String descripcion;
    private int completada; // 0 = no, 1 = sí

    // Constructor vacío
    public SubMeta() {}

    // Constructor completo (para lectura de la BD)
    public SubMeta(int id, int metaId, String titulo, String descripcion, int completada) {
        this.id = id;
        this.metaId = metaId;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    // Constructor para crear nueva SubMeta (inserción)
    public SubMeta(String titulo, String descripcion, boolean completada, int metaId) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada ? 1 : 0;
        this.metaId = metaId;
    }

    // Constructor simplificado
    public SubMeta(String titulo, boolean completada, int metaId) {
        this.titulo = titulo;
        this.descripcion = "";
        this.completada = completada ? 1 : 0;
        this.metaId = metaId;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMetaId() { return metaId; }
    public void setMetaId(int metaId) { this.metaId = metaId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getCompletada() { return completada; }
    public void setCompletada(int completada) { this.completada = completada; }

    public boolean isCompletada() { return completada == 1; }
    public void setCompletada(boolean completada) { this.completada = completada ? 1 : 0; }
}
