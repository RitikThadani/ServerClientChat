package com.example.chatritikfinal2.Principal.Conections;

import com.example.chatritikfinal2.Principal.ControladorBaseDatos;
import com.example.chatritikfinal2.Struct.Mensaje;
import com.example.chatritikfinal2.Struct.Usuario;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.chatritikfinal2.Principal.ControladorBaseDatos.comprobarUsuario;

/**
 * Clase que representa el servidor de la aplicación
 */

public class Server {

    /**
     * Método principal que inicia el servidor
     * @param args
     */

    public static void main(String[] args) {
        // Lista de mensajes
        ArrayList<Mensaje> listaMensajes = new ArrayList<>();
        // Puerto de conexión
        int puerto = 5000;
        // Instancia de Gson para la serialización/deserialización de objetos
        Gson gson = new Gson();

        try (ServerSocket servidor = new ServerSocket(puerto)) { // Inicio del servidor en el puerto especificado
            System.out.println("Servidor Iniciado");
            while (true) { // Ciclo para aceptar conexiones continuamente
                try (Socket sc = servidor.accept(); // Acepta una conexión entrante
                     DataInputStream in = new DataInputStream(sc.getInputStream()); // Stream de entrada para recibir datos
                     DataOutputStream out = new DataOutputStream(sc.getOutputStream())) { // Stream de salida para enviar datos

                    int operationCode = in.readInt(); // Lee el código de operación enviado por el cliente
                    switch (operationCode) {
                        case 1:
                            manejoMensaje(in, out, gson); // Maneja el caso de envío de mensaje
                            break;
                        case 2:
                            manejoEnvioMensaje(in, out, listaMensajes); // Maneja el caso de enviar mensaje
                            break;
                        case 3:
                            manejoLogin(in, out, gson); // Maneja el caso de inicio de sesión
                            break;
                        case 4:
                            manejoGetAllUsers(out, gson); // Maneja el caso de obtener todos los usuarios
                            break;
                        default:
                            System.out.println("Operación no Válida"); // Si el código de operación no es válido
                            break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Método para manejar el envío de un mensaje
     * @param in
     * @param out
     * @param gson
     * @throws IOException
     */

    private static void manejoMensaje(DataInputStream in, DataOutputStream out, Gson gson) throws IOException {
        String jsonMessage = in.readUTF(); // Lee el mensaje en formato JSON
        Mensaje mensaje = gson.fromJson(jsonMessage, Mensaje.class); // Convierte el JSON a un objeto Mensaje
        out.writeUTF("Mensaje Recibido"); // Envía una confirmación al cliente
        ControladorBaseDatos.insertarMensaje(mensaje.getIdRemitente(), mensaje.getIdDestinatario(), mensaje.getMensaje()); // Inserta el mensaje en la base de datos
    }

    /**
     * Método para manejar el envío de mensajes
     * @param in
     * @param out
     * @param listMensaje
     * @throws IOException
     */

    private static void manejoEnvioMensaje(DataInputStream in, DataOutputStream out, ArrayList<Mensaje> listMensaje) throws IOException {
        int id = in.readInt(); // Lee el ID del remitente
        int id2 = in.readInt(); // Lee el ID del destinatario
        if (id2 != 0) { // Si hay un destinatario válido
            enviarMensaje(out, listMensaje, id, id2); // Envía los mensajes al destinatario
        } else {
            out.writeUTF("");
        }
    }

    /**
     * Método para manejar el inicio de sesión de usuario
     * @param in
     * @param out
     * @param gson
     * @throws IOException
     */

    private static void manejoLogin(DataInputStream in, DataOutputStream out, Gson gson) throws IOException {
        String userName = in.readUTF(); // Lee el nombre de usuario
        String userPassword = in.readUTF(); // Lee la contraseña
        Usuario usuario = comprobarUsuario(userName, userPassword); // Verifica las credenciales del usuario
        String userJson = gson.toJson(usuario); // Convierte el usuario a JSON
        out.writeUTF(userJson); // Envía el usuario al cliente
    }

    /**
     * Método para manejar la solicitud de todos los usuarios
     * @param out
     * @param gson
     * @throws IOException
     */

    private static void manejoGetAllUsers(DataOutputStream out, Gson gson) throws IOException {
        ArrayList<Usuario> usuarios = ControladorBaseDatos.getUsers(); // Obtiene todos los usuarios de la base de datos
        String allUsers = gson.toJson(usuarios); // Convierte la lista de usuarios a JSON
        out.writeUTF(allUsers); // Envía la lista de usuarios al cliente
    }

    /**
     * Método para enviar mensajes
     * @param out
     * @param listMensaje
     * @param id
     * @param id2
     * @throws IOException
     */

    private static void enviarMensaje(DataOutputStream out, ArrayList<Mensaje> listMensaje, int id, int id2) throws IOException {
        listMensaje.clear(); // Limpia la lista de mensajes
        listMensaje = ControladorBaseDatos.getConversacion(id, id2); // Obtiene la conversación entre el remitente y el destinatario
        Gson gson = new Gson(); // Instancia Gson para la serialización de objetos
        String jsonListMessage = gson.toJson(listMensaje); // Convierte la lista de mensajes a JSON
        out.writeUTF(jsonListMessage); // Envía la lista de mensajes al cliente
    }
}
