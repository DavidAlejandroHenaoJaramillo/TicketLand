module com.example.ticketland {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.ticketland to javafx.fxml;
    exports com.example.ticketland;
}