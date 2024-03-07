package com.example.chatritikfinal2.Controladores;

import com.example.chatritikfinal2.Principal.Conections.Cliente;
import com.example.chatritikfinal2.Struct.Mensaje;
import com.example.chatritikfinal2.Struct.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Chat implements Initializable {

    /*
    Declaración de los elementos de la interfaz de usuario
     */

    @FXML
    public TextField msgField; // Campo de texto para ingresar mensajes
    @FXML
    public TextArea conversacion; // Área de texto para mostrar la conversación
    @FXML
    public TableView<Usuario> usTable; // Tabla para mostrar la lista de usuarios

    /*
    Variables usadas.
     */

    public TableColumn<Usuario, String> usersNamesColumn; // Columna de nombres de usuario en la tabla
    public static Usuario usuario; // Usuario actual
    public static Usuario usuario2; // Usuario seleccionado para la conversación
    public static Cliente cliente = new Cliente(); // Cliente para interactuar con el servidor

    /**
     * Método para enviar un mensaje
     */

    public void enviarMensaje() {
        // Crear un nuevo mensaje con el ID del remitente, ID del destinatario y el texto del mensaje
        Mensaje m = new Mensaje(usuario.getId(), usuario2.getId(), msgField.getText());
        // Enviar el mensaje al servidor
        cliente.enviarMensaje(m);
        // Limpiar el área de texto del mensaje
        msgField.clear();
    }

    /**
     * Método para recibir mensajes del servidor
     */

    public void getMensajes() {
        // Obtener la lista de mensajes del servidor
        ArrayList<Mensaje> listMensaje = cliente.recibirMensaje();
        // Llenar el área de texto de la conversación con los mensajes recibidos
        obtenerConversacion(listMensaje);
    }

    /**
     * Método para llenar la tabla de usuarios
     */

    public void obtenerTablaUs() {
        // Obtener la lista de usuarios del servidor y convertirla en una lista observable
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList(cliente.getUsers());
        // Configurar la celda de la tabla para mostrar los nombres de usuario
        usersNamesColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        // Establecer los datos de la tabla con la lista de usuarios
        usTable.setItems(usuarios);
    }

    /**
     * Método para llenar el área de texto de la conversación con los mensajes recibidos
     * @param listMensaje
     */

    public void obtenerConversacion(ArrayList<Mensaje> listMensaje) {
        // Crear un StringBuilder para construir el texto de la conversación
        StringBuilder conversationText = new StringBuilder();
        // Iterar sobre la lista de mensajes y agregarlos al StringBuilder
        for (Mensaje mensaje : listMensaje) {
            conversationText.append(mensaje.getIdRemitente())
                    .append(": ")
                    .append(mensaje.getMensaje())
                    .append("\n");
        }
        // Establecer el texto construido en el área de texto de la conversación
        conversacion.setText(conversationText.toString());
    }

    /**
     * Método para recibir mensajes del servidor cada 3 segundos
     */
    private void bucleActualizar() {
        // Ciclo infinito para recibir mensajes cada 3 segundos
        while (true) {
            try {
                // Recibir mensajes del servidor
                getMensajes();
                // Imprimir el ID del usuario seleccionado si está disponible
                if (usuario2 != null) {
                    System.out.println(usuario2.getId());
                }
                // Esperar 4 segundos antes de la próxima recepción de mensajes
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para cambiar el usuario seleccionado para la conversación
     */

    public void changeUser() {
        // Obtener el usuario seleccionado en la tabla
        usuario2 = usTable.getSelectionModel().getSelectedItem();
        // Imprimir el ID y el nombre del usuario seleccionado
        System.out.println(usuario2.getId() + usuario2.getName());
    }

    /**
     * Método para inicializar la ventana
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Iniciar un hilo para ejecutar la tarea periódica de recibir mensajes del servidor
        Thread thread = new Thread(this::bucleActualizar);
        thread.start();
        // Llenar la tabla de usuarios con los usuarios disponibles en el servidor
        obtenerTablaUs();
    }
}
