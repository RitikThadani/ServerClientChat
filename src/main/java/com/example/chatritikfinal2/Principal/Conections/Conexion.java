package com.example.chatritikfinal2.Principal.Conections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    // Definición de las constantes para la URL de conexión, nombre de usuario y contraseña de la base de datos
    private static final String USER = "root";
    private static final String PASS = "1234";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/chat?characterEncoding=utf8";

    // Establecimiento de la conexión estática
    public static Connection con = conectar();

    /**
     * Método para establecer la conexión con la base de datos
     * @return
     */

    public static Connection conectar() {
        Connection con = null; // Inicialización de la conexión a null
        try {
            con = DriverManager.getConnection(URL, USER, PASS); // Intento de conexión a la base de datos
            System.out.println("Conectado"); // Mensaje de éxito en la conexión
        } catch (SQLException ex) { // Captura de excepciones de SQL
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex); // Registro de la excepción
        }
        return con; // Devolución de la conexión (puede ser null si la conexión falló)
    }
}
