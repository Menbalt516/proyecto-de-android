package com.m.d.f.miagenda.modelos;

public class SubMetaEvento {
    private int id;
    private int subMetaId;
    private int eventoId;

    public SubMetaEvento() {
    }

    public SubMetaEvento(int id, int subMetaId, int eventoId) {
        this.id = id;
        this.subMetaId = subMetaId;
        this.eventoId = eventoId;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubMetaId() {
        return subMetaId;
    }

    public void setSubMetaId(int subMetaId) {
        this.subMetaId = subMetaId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }
}