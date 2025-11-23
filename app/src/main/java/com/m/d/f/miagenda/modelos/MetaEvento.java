package com.m.d.f.miagenda.modelos;

public class MetaEvento {
    private int id;
    private int metaId;
    private int eventoId;

    public MetaEvento() {}

    public MetaEvento(int id, int metaId, int eventoId) {
        this.id = id;
        this.metaId = metaId;
        this.eventoId = eventoId;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMetaId() { return metaId; }
    public void setMetaId(int metaId) { this.metaId = metaId; }

    public int getEventoId() { return eventoId; }
    public void setEventoId(int eventoId) { this.eventoId = eventoId; }
}


