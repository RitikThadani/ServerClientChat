package com.example.chatritikfinal2.Principal;

import com.example.chatritikfinal2.Struct.Mensaje;
import com.example.chatritikfinal2.Struct.Usuario;
import com.example.chatritikfinal2.Principal.Conections.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControladorBaseDatos {
    // Establece una conexión estática a la base de datos
    private static final Connection connection = Conexion.con;

    /**
     * Método privado para ejecutar una consulta SQL de mensajes y devolver los resultados como una lista de mensajes
     * @param query
     * @param params
     * @return
     */

    private static ArrayList<Mensaje> executeMensajeQuery(String query, int... params) {
        ArrayList<Mensaje> listMensaje = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            // Establece los parámetros de la consulta
            for (int i = 0; i < params.length; i++) {
                stm.setInt(i + 1, params[i]);
            }
            try (ResultSet result = stm.executeQuery()) {
                // Itera sobre los resultados y crea objetos Mensaje
                while (result.next()) {
                    int idSender = result.getInt(1);
                    int idReceptor = result.getInt(2);
                    String message = result.getString(3);
                    Mensaje m = new Mensaje(idSender, idReceptor, message);
                    listMensaje.add(m);
                }
            }
        } catch (SQLException e) {
            // Captura y lanza cualquier excepción SQL
            throw new RuntimeException(e);
        }
        // Devuelve la lista de mensajes
        return listMensaje;
    }

    /**
     * Método para obtener la conversación entre dos usuarios
     * @param id
     * @param id2
     * @return
     */

    public static ArrayList<Mensaje> getConversacion(int id, int id2){
        // Consulta SQL para seleccionar los mensajes entre dos usuarios
        String query = "select id_remitente, id_destinatario, mensaje from mensajes where (id_remitente = ? AND id_destinatario = ?) OR (id_remitente = ? AND id_destinatario = ?) order by idmsg;";
        // Ejecuta la consulta y devuelve los resultados
        return executeMensajeQuery(query, id, id2, id2, id);
    }

    /**
     * Método privado para ejecutar una consulta SQL de usuarios y devolver los resultados como una lista de usuarios
     * @param query
     * @return
     */

    private static ArrayList<Usuario> executeUsuarioQuery(String query) {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try (PreparedStatement stm = connection.prepareStatement(query);
             ResultSet result = stm.executeQuery()) {
            // Itera sobre los resultados y crea objetos Usuario
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                usuarios.add(new Usuario(id, name));
            }
        } catch (SQLException e) {
            // Captura y lanza cualquier excepción SQL
            throw new RuntimeException(e);
        }
        // Devuelve la lista de usuarios
        return usuarios;
    }

    /**
     * Método para obtener todos los usuarios de la base de datos
     * @return
     */

    public static ArrayList<Usuario> getUsers(){
        // Consulta SQL para seleccionar todos los usuarios
        String query = "select * from usuarios;";
        // Ejecuta la consulta y devuelve los resultados
        return executeUsuarioQuery(query);
    }

    /**
     * Método para verificar si un usuario existe en la base de datos
     * @param userName
     * @param userPassword
     * @return
     */

    public static Usuario comprobarUsuario(String userName, String userPassword){
        // Consulta SQL para buscar un usuario por nombre de usuario y contraseña
        String query = "select * from usuarios where nombre=? and contra=?;";
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            // Establece los parámetros de la consulta
            stm.setString(1, userName);
            stm.setString(2, userPassword);
            try (ResultSet result = stm.executeQuery()) {
                if (result.next()) {
                    // Si se encuentra un usuario, crea un objeto Usuario y lo devuelve
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    return new Usuario(id, name);
                }
            }
        } catch (SQLException e) {
            // Captura y lanza cualquier excepción SQL
            throw new RuntimeException(e);
        }
        // Si no se encuentra ningún usuario, devuelve null
        return null;
    }

    /**
     * Método para insertar un mensaje en la base de datos
     * @param idSender
     * @param idReceptor
     * @param message
     */

    public static void insertarMensaje(int idSender, int idReceptor, String message){
        // Consulta SQL para insertar un mensaje en la tabla mensajes
        String query = "insert into mensajes values(default,?,?,?,localtime());";
        try (PreparedStatement stm = connection.prepareStatement(query)) {
            // Establece los parámetros de la consulta
            stm.setInt(1, idSender);
            stm.setInt(2, idReceptor);
            stm.setString(3, message);
            // Ejecuta la consulta
            stm.executeUpdate();
        } catch (SQLException e) {
            // Captura y lanza cualquier excepción SQL
            throw new RuntimeException(e);
        }
    }
}
