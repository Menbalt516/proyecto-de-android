package com.m.d.f.miagenda.presentacion.MetaListItem;

public class MetaListItem {
    public enum Tipo { SECCION, ITEM }

    public Tipo tipo;
    public String titulo; // Para sección o item
    public int id; // solo si es ITEM
    public boolean esSubMeta; // solo si es ITEM

    public MetaListItem(Tipo tipo, String titulo) {
        this.tipo = tipo;
        this.titulo = titulo;
    }

    public MetaListItem(int id, String titulo, boolean esSubMeta) {
        this.tipo = Tipo.ITEM;
        this.id = id;
        this.titulo = titulo;
        this.esSubMeta = esSubMeta;
    }
}
