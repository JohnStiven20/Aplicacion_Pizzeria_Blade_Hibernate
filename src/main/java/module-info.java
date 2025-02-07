module aplicacion {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires lombok;
    requires okhttp3;
    requires com.google.gson;
    requires javafx.graphics;

    
    opens aplicacion.controller to javafx.fxml;
    opens aplicacion.modelo to com.google.gson;

    exports aplicacion;
    exports aplicacion.controller;
}
