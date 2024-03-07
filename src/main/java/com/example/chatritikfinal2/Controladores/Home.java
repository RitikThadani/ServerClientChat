package com.example.chatritikfinal2.Controladores;

import com.example.chatritikfinal2.Struct.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Home {

    /*
     Declaración de elementos de la interfaz de usuario
     */

    @FXML
    public TextField usField; // Campo de texto para el nombre de usuario
    @FXML
    public TextField contraField; // Campo de texto para la contraseña del usuario
    @FXML
    public Label msgError; // Etiqueta para mostrar mensajes de error

    /*
    Variables usadas
     */

    public static Usuario usActual; // Usuario que ha iniciado sesión

    /**
     * Método para iniciar sesión de usuario
     */

    public void login() {
        // Crear un nuevo usuario con el nombre de usuario y la contraseña ingresados
        Usuario usuario = new Usuario(usField.getText(), contraField.getText());
        // Establecer el usuario como el usuario que ha iniciado sesión
        usActual = usuario;
        // Intentar iniciar sesión con el servidor
        Usuario usuario1 = Chat.cliente.conectarServidor();
        if (usuario1 != null) { // Si el inicio de sesión es exitoso
            Chat.usuario = usuario1; // Establecer el usuario actual en la sesión de chat
            cambiarVentana(); // Cambiar a la ventana del chat
        } else { // Si el inicio de sesión falla
            msgError.setVisible(true); // Mostrar un mensaje de error en la interfaz de usuario
        }
    }

    /**
     * Método para cambiar a la ventana del chat
     */

    public void cambiarVentana() {
        // Cargar la interfaz de usuario del chat desde el archivo FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatritikfinal2/Chat.fxml"));
        Parent root = null;
        try {
            root = loader.load(); // Cargar la raíz de la interfaz de usuario
            Scene scene = new Scene(root); // Crear una nueva escena con la raíz cargada
            Stage chatScreenStage = new Stage(); // Crear una nueva ventana para la pantalla de chat
            chatScreenStage.setTitle("Chat"); // Establecer el título de la ventana
            chatScreenStage.setResizable(false); // Desactivar la capacidad de cambiar el tamaño de la ventana
            chatScreenStage.setScene(scene); // Establecer la escena en la ventana
            chatScreenStage.show(); // Mostrar la ventana de la pantalla de chat
            Stage ventanaActual = (Stage) this.usField.getScene().getWindow(); // Obtener la ventana actual
            ventanaActual.close(); // Cerrar la ventana actual
        } catch (IOException e) { // Manejar cualquier excepción de E / S que pueda ocurrir durante la carga de la interfaz de usuario
            System.out.println("Error al Cambiar de Ventana"); // Imprimir un mensaje de error en la consola
        }
    }
}
