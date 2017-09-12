package Lab1Gui;

import lab1.*;
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
 *
 * @author kkcckc
 */
public class Gui extends Application {

    private double xOffset = 0;
    private double yOffset = 0;
//    private Boolean resizebottom = false;
//    private double dx;
//    private double dy;
    private double maxWidth = 14000;
    private double maxHeight = 8000;
    private double minWidth = 763   ;
    private double minHeight = 540;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Lab ONE");
        
Image icon = new Image(getClass().getResourceAsStream("start.png"));
stage.getIcons().add(icon);
       // stage.getIcons().add(new Image("/home/kkcckc/Desktop/dot/out.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getResource("Gui.fxml"));
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
//        press enter to run
//        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
//            if (event.getCode().equals(KeyCode.ENTER)) {
//                //System.out.println("Lab1Gui.Gui.start()");
//                System.out.println("Lab1Gui.Gui.start()");
//            }
//            });
        //ResizeHelper.addResizeListener(stage,minWidth,minHeight,maxWidth,maxHeight);

//        root.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                xOffset = event.getSceneX();
//                yOffset = event.getSceneY();
////                if (event.getX() > stage.getWidth() - 10
////                        && event.getX() < stage.getWidth() + 10
////                        && event.getY() > stage.getHeight() - 10
////                        && event.getY() < stage.getHeight() + 10) {
////                    resizebottom = true;
////                    dx = stage.getWidth() - event.getX();
////                    dy = stage.getHeight() - event.getY();
////                } else {
////                    resizebottom = false;
////                    xOffset = event.getSceneX();
////                    yOffset = event.getSceneY();
////                }
//            }
//        });
//
//        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                stage.setX(event.getScreenX() - xOffset);
//                stage.setY(event.getScreenY() - yOffset);
////                if (resizebottom == false) {
////                    stage.setX(event.getScreenX() - xOffset);
////                    stage.setY(event.getScreenY() - yOffset);
////                } else if (){
////                    stage.setWidth(event.getX() + dx);
////                    stage.setHeight(event.getY() + dy);
////                }
//            }
//        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
