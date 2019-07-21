package com.example.parcial1;

public class Model {
    private int id;
    private String nombre;
    private String apellido;
    private String numero;

    public Model(int id, String nombre, String apellido, String numero) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numero = numero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
