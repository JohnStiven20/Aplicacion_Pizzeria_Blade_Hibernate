module aplicacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;
    requires okhttp3;
    requires com.google.gson;
    requires javafx.graphics;

    // Abre paquetes específicos para acceso reflexivo
    opens aplicacion.controller to javafx.fxml;
    opens aplicacion.modelo to com.google.gson;

    // Exporta los paquetes necesarios
    exports aplicacion;
    exports aplicacion.controller;
}
