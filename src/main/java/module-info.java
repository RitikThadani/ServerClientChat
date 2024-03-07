module com.example.chatritikfinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;

    opens com.example.chatritikfinal2 to javafx.fxml, com.google.gson;
    exports com.example.chatritikfinal2;
    exports com.example.chatritikfinal2.Controladores;
    opens com.example.chatritikfinal2.Controladores to javafx.fxml, com.google.gson;
    opens com.example.chatritikfinal2.Principal to com.google.gson, javafx.base;
    opens com.example.chatritikfinal2.Principal.Conections to com.google.gson;
    opens com.example.chatritikfinal2.Struct to com.google.gson, javafx.base;
}