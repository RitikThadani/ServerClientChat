package com.example.chatritikfinal2.Struct;

/**
 * Clase que representa un mensaje enviado entre usuarios
 */

public class Mensaje {
    // Atributos del mensaje
    public int idRemitente; // ID del remitente

    public int idDestinatario; // ID del receptor

    public String mensaje; // Contenido del mensaje

    /*
    Constructor de la clase Mensaje
     */

    public Mensaje(int idRemitente, int idDestinatario, String mensaje) {
        this.idRemitente = idRemitente;
        this.idDestinatario = idDestinatario;
        this.mensaje = mensaje;
    }

    /*
    Getters y Setters
     */

    public int getIdRemitente() {
        return idRemitente;
    }

    public int getIdDestinatario() {
        return idDestinatario;
    }

    public String getMensaje() {
        return mensaje;
    }
}
