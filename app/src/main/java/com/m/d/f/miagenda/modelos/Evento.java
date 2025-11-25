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
    private boolean recordatorio;
    private int metaId = -1;

    public Evento() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public long getFecha() { return fechaMillis; }
    public void setFecha(long fechaMillis) { this.fechaMillis = fechaMillis; }


    // ⬅️ MÉTODO NECESARIO PARA LA NOTIFICACIÓN
    public long getFechaEnMilisegundos() {
        return fechaMillis;
    }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public boolean isRecordatorio() { return recordatorio; }
    public void setRecordatorio(boolean recordatorio) { this.recordatorio = recordatorio; }

    public int getMetaId() { return metaId; }
    public void setMetaId(int metaId) { this.metaId = metaId; }

    public String getFechaString() {
        Date date = new Date(fechaMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(date);
    }
}
