package com.example.chatritikfinal2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Clase principal que inicia la aplicación y carga la escena Home.fxml
 */

public class User extends Application {

    /**
     * Método start() que es llamado cuando se inicia la aplicación
     * @param stage
     * @throws IOException
     */

    @Override
    public void start(Stage stage) throws IOException {
        // Cargar el archivo FXML que define la interfaz de usuario
        FXMLLoader fxmlLoader = new FXMLLoader(User.class.getResource("Home.fxml"));
        Scene scene = new Scene(fxmlLoader.load()); // Crear la escena con el contenido del FXML
        stage.setTitle("Hola!"); // Establecer el título de la ventana
        stage.setScene(scene); // Establecer la escena en el escenario
        stage.show(); // Mostrar la ventana
    }

    /**
     * Método main() para iniciar la aplicación
     * @param args
     */

    public static void main(String[] args) {
        launch(); // Lanzar la aplicación
    }
}
