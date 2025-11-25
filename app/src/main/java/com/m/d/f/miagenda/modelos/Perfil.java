package com.m.d.f.miagenda.modelos;

public class Perfil {
    private int id; // Siempre será 1
    private String nombre;
    private String apellidos;
    private int edad;
    private String imagenUri; // Guardado como String (ruta/URI)

    public Perfil() {}

    public Perfil(int id, String nombre, String apellidos, int edad, String imagenUri) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.imagenUri = imagenUri;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getImagenUri() { return imagenUri; }
    public void setImagenUri(String imagenUri) { this.imagenUri = imagenUri; }
}
