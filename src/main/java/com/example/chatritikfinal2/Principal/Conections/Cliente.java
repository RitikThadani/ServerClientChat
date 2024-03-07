package com.example.chatritikfinal2.Principal.Conections;

import com.example.chatritikfinal2.Controladores.Chat;
import com.example.chatritikfinal2.Controladores.Home;
import com.example.chatritikfinal2.Struct.Mensaje;
import com.example.chatritikfinal2.Struct.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {
    private static final String HOST = "127.0.0.1"; // Dirección IP del servidor
    private static final int PUERTO = 5000; // Puerto del servidor
    private DataInputStream in; // Stream de entrada para recibir datos del servidor
    private DataOutputStream out; // Stream de salida para enviar datos al servidor

    /**
     * Método para iniciar sesión en el servidor
     * @return
     */

    public Usuario conectarServidor() {
        Usuario usuario = Home.usActual; // Obtener el usuario que ha iniciado sesión
        try (Socket sc = new Socket(HOST, PUERTO)) { // Crear un nuevo socket para conectarse al servidor
            in = new DataInputStream(sc.getInputStream()); // Obtener el stream de entrada del socket
            out = new DataOutputStream(sc.getOutputStream()); // Obtener el stream de salida del socket
            Gson gson = new Gson(); // Instanciar un objeto Gson para serializar el usuario
            out.writeInt(3); // Enviar el código de operación para iniciar sesión al servidor
            out.writeUTF(usuario.getName()); // Enviar el nombre de usuario al servidor
            out.writeUTF(usuario.getPassword()); // Enviar la contraseña de usuario al servidor
            String response = in.readUTF(); // Leer la respuesta del servidor
            Usuario usuario1 = gson.fromJson(response, Usuario.class); // Deserializar la respuesta del servidor en un objeto Usuario
            return usuario1; // Devolver el usuario obtenido del servidor
        } catch (IOException e) { // Manejar cualquier excepción de E/S que pueda ocurrir
            throw new RuntimeException(e); // Lanzar una excepción de tiempo de ejecución
        }
    }

    /**
     * Método para obtener la lista de usuarios del servidor
     * @return
     */

    public ArrayList<Usuario> getUsers() {
        try (Socket sc = new Socket(HOST, PUERTO)) { // Crear un nuevo socket para conectarse al servidor
            in = new DataInputStream(sc.getInputStream()); // Obtener el stream de entrada del socket
            out = new DataOutputStream(sc.getOutputStream()); // Obtener el stream de salida del socket
            Gson gson = new Gson(); // Instanciar un objeto Gson para deserializar la lista de usuarios
            out.writeInt(4); // Enviar el código de operación para obtener la lista de usuarios al servidor
            String response = in.readUTF(); // Leer la respuesta del servidor
            ArrayList<Usuario> usuarios = gson.fromJson(response, new TypeToken<ArrayList<Usuario>>() {}.getType()); // Deserializar la lista de usuarios del servidor
            return usuarios; // Devolver la lista de usuarios obtenida del servidor
        } catch (IOException e) { // Manejar cualquier excepción de E/S que pueda ocurrir
            throw new RuntimeException(e); // Lanzar una excepción de tiempo de ejecución
        }
    }

    /**
     * Método para enviar un mensaje al servidor
     * @param mensaje
     */

    public void enviarMensaje(Mensaje mensaje) {
        try (Socket sc = new Socket(HOST, PUERTO)) { // Crear un nuevo socket para conectarse al servidor
            in = new DataInputStream(sc.getInputStream()); // Obtener el stream de entrada del socket
            out = new DataOutputStream(sc.getOutputStream()); // Obtener el stream de salida del socket
            Gson gson = new Gson(); // Instanciar un objeto Gson para serializar el mensaje
            out.writeInt(1); // Enviar el código de operación para enviar un mensaje al servidor
            out.writeUTF(gson.toJson(mensaje)); // Serializar el mensaje y enviarlo al servidor
            String response = in.readUTF(); // Leer la respuesta del servidor
        } catch (IOException e) { // Manejar cualquier excepción de E/S que pueda ocurrir
            throw new RuntimeException(e); // Lanzar una excepción de tiempo de ejecución
        }
    }

    /**
     * Método para recibir mensajes del servidor
     * @return
     */

    public ArrayList<Mensaje> recibirMensaje() {
        ArrayList<Mensaje> listMensaje = new ArrayList<>(); // Crear una lista para almacenar los mensajes recibidos
        try (Socket sc = new Socket(HOST, PUERTO)) { // Crear un nuevo socket para conectarse al servidor
            in = new DataInputStream(sc.getInputStream()); // Obtener el stream de entrada del socket
            out = new DataOutputStream(sc.getOutputStream()); // Obtener el stream de salida del socket
            out.writeInt(2); // Enviar el código de operación para recibir un mensaje al servidor
            out.writeInt(Chat.usuario.getId()); // Enviar el ID del usuario actual al servidor
            out.writeInt(Chat.usuario2 != null ? Chat.usuario2.getId() : 0); // Enviar el ID del usuario destinatario al servidor (si es null, enviar 0)
            String message = in.readUTF(); // Leer el mensaje del servidor
            if (Chat.usuario2 == null) { // Si el usuario destinatario es nulo (mensaje general)
                System.out.println(message); // Imprimir el mensaje en la consola
            } else { // Si hay un usuario destinatario específico
                Gson gson = new Gson(); // Instanciar un objeto Gson para deserializar el mensaje
                listMensaje = gson.fromJson(message, new TypeToken<ArrayList<Mensaje>>() {}.getType()); // Deserializar el mensaje en una lista de mensajes
            }
        } catch (IOException e) { // Manejar cualquier excepción de E/S que pueda ocurrir
            throw new RuntimeException(e); // Lanzar una excepción de tiempo de ejecución
        }
        return listMensaje; // Devolver la lista de mensajes recibidos
    }
}
