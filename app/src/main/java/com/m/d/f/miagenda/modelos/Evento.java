package com.m.d.f.miagenda.modelos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Evento {
    private int id;
    private String titulo;
    private long fechaMillis;
    private String hora;
    private String descripcion;
    private String categoria;
    private int recordatorio; // 0 = no, 1 = sí
    private int metaAsociadaId;

    public Evento() {}

    public Evento(int id, String titulo, long fechaMillis, String hora, String descripcion,
                  String categoria, int recordatorio, int metaAsociadaId) {
        this.id = id;
        this.titulo = titulo;
        this.fechaMillis = fechaMillis;
        this.hora = hora;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.recordatorio = recordatorio;
        this.metaAsociadaId = metaAsociadaId;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public long getFechaMillis() { return fechaMillis; }
    public void setFechaMillis(long fechaMillis) { this.fechaMillis = fechaMillis; }

    // Retorna la fecha como String para mostrar en la UI
    public String getFecha() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(new Date(fechaMillis));
    }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getRecordatorio() { return recordatorio; }
    public void setRecordatorio(int recordatorio) { this.recordatorio = recordatorio; }

    public int getMetaAsociadaId() { return metaAsociadaId; }
    public void setMetaAsociadaId(int metaAsociadaId) { this.metaAsociadaId = metaAsociadaId; }
}
