package com.m.d.f.miagenda.modelos;

public class SubMeta {
    private int id;
    private int metaId;
    private String titulo;
    private String descripcion;
    private boolean completada;

    public SubMeta(){}

    // getters/setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMetaId() { return metaId; }
    public void setMetaId(int metaId) { this.metaId = metaId; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public boolean isCompletada() {return completada;}
    public void setCompletada(boolean completada) { this.completada = completada; }
}
