package com.example.chatritikfinal2.Struct;

/**
 * Clase que representa a un usuario del sistema
 */

public class Usuario {
    // Atributos de la clase Usuario
    private int id; // ID del usuario
    private String name; // Nombre del usuario
    private String password; // Contraseña del usuario

    /*
    Constructor para crear un usuario con ID y nombre
     */

    public Usuario(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /*
    Constructor para crear un usuario con nombre y contraseña
     */

    public Usuario(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /*
    Getters y Setters
     */

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}

