module com.example.ticketland {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;

    opens com.example.ticketland to javafx.fxml;
    exports com.example.ticketland;
}