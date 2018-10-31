package sample;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String tittle, String message){
        Stage window = new Stage();
        window.setTitle(tittle);
        window.setMinHeight(100);
        window.setMinWidth(300);

        Label label = new Label();
        Label label1 = new Label();
        label.setText(message);
        Button buttonOk = new Button("OK");
        buttonOk.setOnAction(e -> window.close());
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,buttonOk);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();


    }
}
