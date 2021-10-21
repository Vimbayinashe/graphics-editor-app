module com.draw.canvas.labb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.draw.canvas.labb3 to javafx.fxml;
    exports com.draw.canvas.labb3;
}