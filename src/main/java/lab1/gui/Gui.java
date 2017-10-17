package lab1.gui;
import lab1.core.*;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 * @author kkcckc
 */
public class Gui extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Lab ONE");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/start.png")));
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("/Gui.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    } 
    public static void main(String[] args) {
        launch(args);
    }

}
