/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lab1Gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kkcckc
 */
public class BigImageController implements Initializable {

    @FXML
    private AnchorPane biPanel;
    @FXML
    private ImageView biImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Image img=null;
        try {
            img = new Image(new FileInputStream("out.png"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BigImageController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        biImage.setImage(img);
        biPanel.setMinWidth(0);
        //biPanel.setPrefWidth(img.getWidth());
        //biPanel.setCenterShape(true);
    }

    @FXML
    private void keyPressed(KeyEvent event) {
        ((Stage) biImage.getScene().getWindow()).close();
    }

    @FXML
    private void mouseClicked(MouseEvent event) {
        //((Stage) image.getScene().getWindow()).close();
    }

    @FXML
    private void closeAll(ActionEvent event) {
        ((Stage) biImage.getScene().getWindow()).close();
    }

    public void undateImage() {
        try {
            biImage.setImage(new Image(new FileInputStream("out.png")));
        } catch (FileNotFoundException e) {
            //biText.setStyle("-fx-fill:  #c62828");
        }
    }
}
