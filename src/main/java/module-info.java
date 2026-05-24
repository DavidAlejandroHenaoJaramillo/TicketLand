module com.example.ticketland {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;

    opens com.example.ticketland to javafx.fxml;
    exports com.example.ticketland;
    opens facade;
    opens adapter;
    opens model;
    opens factory;
    opens observer;
    opens state;
    opens strategy;
    opens decorator;
}