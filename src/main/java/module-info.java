module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chat to javafx.fxml;
    exports com.example.chat;
}