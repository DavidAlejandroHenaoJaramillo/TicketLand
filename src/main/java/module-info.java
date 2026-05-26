module com.example.ticketland {
    requires javafx.controls;
    requires javafx.fxml;


    exports com.example.ticketland;
<<<<<<< Updated upstream
=======
    exports controller;
    exports viewController;
    exports model;

    opens com.example.ticketland to javafx.fxml;
    opens viewController to javafx.fxml;
    opens facade;
    opens adapter;
    opens model;
    opens factory;
    opens observer;
    opens state;
    opens strategy;
    opens decorator;
    opens builder;
    opens proxy;
    opens prototype;
>>>>>>> Stashed changes
}