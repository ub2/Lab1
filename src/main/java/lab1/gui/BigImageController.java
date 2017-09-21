package lab1.gui;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kkcckc
 */
public class BigImageController implements Initializable {

    @FXML
    private ImageView biImage;
    @FXML
    private AnchorPane rootPanel;
    @FXML
    private JFXButton closeButton;
    @FXML
    private OctIconView closeX;

    private boolean init = true;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        biImage.setPreserveRatio(true);
        rootPanel.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            Scene scene = rootPanel.getScene();
            if (e.getSceneX() > scene.getWidth() - border && e.getSceneY() > scene.getHeight() - border) {
                scene.setCursor(Cursor.SE_RESIZE);
            } else {
                scene.setCursor(Cursor.DEFAULT);
            }
        });
    }
    private double border = 14;

    private void keyPressed(KeyEvent event) {
        ((Stage) biImage.getScene().getWindow()).close();
    }

    @FXML
    private void closeAll(ActionEvent event) {
        ((Stage) closeButton.getScene().getWindow()).close();
    }

    public class updateImageThread implements Runnable {

        @Override
        public void run() {
            try {
                final Image img = new Image(new FileInputStream("out.png"));
                Platform.runLater(() -> {
                    biImage.setImage(img);

                });
            } catch (FileNotFoundException e) {
            }
        }

    }

    public void updateImage() {
        if (init) {
            Stage stage = (Stage) biImage.getScene().getWindow();
            stage.widthProperty().addListener(((ov, t, t1) -> {
                biImage.setFitWidth((Double) t1 - 10);
            }));
            stage.heightProperty().addListener(((ov, t, t1) -> {
                biImage.setFitHeight((Double) t1 - 10);
            }));
            init = false;
        }
        new Thread(new updateImageThread()).start();
    }
    private double xOffset = 0, yOffset = 0, startX = 0, startY = 0, stageX = 0, stageY = 0;

    @FXML
    private void panelMousePressed(MouseEvent event) {
        Stage stage = (Stage) (rootPanel.getScene().getWindow());
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
        startX = stage.getWidth() - xOffset;
        startY = stage.getHeight() - yOffset;

    }

    @FXML
    private void PanelMouseDraged(MouseEvent event) {
        Scene scene = rootPanel.getScene();
        Stage stage = (Stage) scene.getWindow();
        Cursor cursorEvent = scene.getCursor();
        Boolean isSE = cursorEvent.equals(Cursor.SE_RESIZE);

        if (!isSE) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } else {
//            System.out.println(event.getSceneX() +" "+ startX+" "+ stageX);
//            stage.setX(stageX);
//            stage.setY(stageY);
//            System.out.println(stage.getWidth());
            stage.setWidth(event.getSceneX() + startX);
            stage.setHeight(event.getSceneY() + startY);
//                        System.out.println(stage.getX());

        }
    }
}
