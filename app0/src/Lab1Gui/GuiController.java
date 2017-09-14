package Lab1Gui;

import lab1.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author kkcckc
 */
public class GuiController implements Initializable {

    private mainWindow lab1;
    private Graph g;
    private AnchorPane winBorder;
    @FXML
    private JFXComboBox<Label> combobox;
    @FXML
    private JFXTextField word2;
    @FXML
    private JFXTextField word1;

    @FXML
    private JFXTextField word3;
    @FXML
    private TextArea fileText;
    @FXML
    private ImageView image;

    @FXML
    private AnchorPane rootPanel;
    @FXML
    private AnchorPane imagePanel;
    @FXML
    private HBox wordBox;
    private Scene scene = null;
    private Stage stage = null;
    @FXML
    private AnchorPane rightPanel;
    @FXML
    private Text tips;
    @FXML
    private OctIconView settingButtton;
    @FXML
    private AnchorPane settingPanel;
    @FXML
    private AnchorPane settingBoard;
    @FXML
    private FontAwesomeIconView saveButton;
    @FXML
    private OctIconView closeX;
    @FXML
    private JFXButton closeButton;
    @FXML
    private JFXCheckBox hideLeftPanel;
    @FXML
    private JFXCheckBox oneShortestWay;
    @FXML
    private JFXCheckBox alwaysWrapText;
    @FXML
    private JFXListView<String> resultList;
    @FXML
    private TextArea resultText;
    @FXML
    private JFXCheckBox systemImageViewer;

    private double sceneWidth = 0,
            sceneHeight = 0,
            startX = 0, startY = 0;
    @FXML
    private JFXButton stopButton;
    @FXML
    private JFXButton pauseButton;
    @FXML
    private FontAwesomeIconView pauseIcon;
    @FXML
    private JFXButton runButton;
    @FXML
    private JFXSlider slider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        scene = rootPanel.getScene();
        combobox.getItems().add(new Label("Query Bridge Words"));
        combobox.getItems().add(new Label("Generate New Text"));
        combobox.getItems().add(new Label("Calc Shortest Path"));
        combobox.getItems().add(new Label("Random Walk"));
        image.setPreserveRatio(true);
        resultList.getItems().add(" ");
        resultList.getItems().clear();
        resultList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    return;
                }
                g.color(newValue.substring(3), 1);
                reloadImage();
            }
        });

        word1.setVisible(false);
        word2.setVisible(false);
        word3.setVisible(false);
        wordBox.setVisible(false);
        lab1 = new mainWindow();
        g = null;
        //fileText
        fileText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (myThread != null) {
                myThread.stop();
            }
            try (PrintWriter out = new PrintWriter("tmp.txt")) {
                out.println(newValue);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            reGenerateGraph();
        });

        JFXDepthManager.setDepth(settingPanel, 1);
        //settingPanel.toFront();
        //settingPanel.toBack();
        //settingPanel.toBack();
        //resize
        rightPanel.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
            scene = rightPanel.getScene();
            if (e.getSceneX() > scene.getWidth() - border && e.getSceneY() > scene.getHeight() - border) {
                scene.setCursor(Cursor.SE_RESIZE);
            } else {
                scene.setCursor(Cursor.DEFAULT);
            }
        });

        //settint panel
        settingBoard.setVisible(false);
        settingBoard.setDisable(true);
        settingButtton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            settingButtton.setStyle("-fx-fill: #00ACC1");
            settingBoard.setVisible(true);
            settingBoard.setDisable(false);
        });

        settingBoard.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            settingButtton.setStyle("-fx-fill: black");
            settingBoard.setVisible(false);
            settingBoard.setDisable(true);
        });

        //save button
        saveButton.setStyle("-fx-fill: #80D8FF");
        saveButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            scene.setCursor(Cursor.HAND);
            saveButton.setStyle("-fx-fill: #00B0FF");
        });
        saveButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            saveButton.setStyle("-fx-fill: #80D8FF");
            scene.setCursor(Cursor.DEFAULT);
        });
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            saveButton.setStyle("-fx-fill: #0277BD");
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date date = new Date();
                Files.copy(new File("out.png").toPath(),
                        new File(System.getProperty("user.home") + File.separator + "Desktop" + File.separator + dateFormat.format(date) + ".png").toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //closeX
        closeX.setStyle("-fx-fill: black");
        closeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            closeX.setStyle("-fx-fill: white");
        });
        closeButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            closeX.setStyle("-fx-fill: black");
        });

        //checkbox action
        hideLeftPanel.addEventFilter(ActionEvent.ACTION, e -> {
            //still need improvement！
            stage = (Stage) hideLeftPanel.getScene().getWindow();
            stage.hide();
            //scene
            if (hideLeftPanel.isSelected()) {
                closeImagePanel();
            } else {
                openImagePanel();
            }
            stage.show();
        });

        oneShortestWay.addEventFilter(ActionEvent.ACTION, e -> {
            if (oneShortestWay.isSelected()) {
                resultText.setVisible(true);
                resultList.getSelectionModel().selectFirst();
                g.color(tempString, colorMode);
            } else {
                resultText.setVisible(false);
                resultList.getSelectionModel().selectFirst();
                g.color(tempString, colorMode);
            }
            reloadImage();
        });

        stopButton.addEventFilter(ActionEvent.ACTION, e -> {
            pauseIcon.setGlyphName("PAUSE");
            pauseIcon.setStyle("-fx-fill: #673ab7");
            if (myThread != null) {
                myThread.stop();
            }
        });

        pauseButton.addEventFilter(ActionEvent.ACTION, e -> {
            if (pauseIcon.getGlyphName().equals("PAUSE")) {
                pauseIcon.setGlyphName("PLAY");
                pauseIcon.setStyle("-fx-fill: #40C4FF");
                try {
                    myThread.pause();
                } catch (InterruptedException ex) {
                    Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                pauseIcon.setGlyphName("PAUSE");
                pauseIcon.setStyle("-fx-fill: #673ab7");
                myThread.resume();
            }
        });

        slider.setVisible(false);
        slider.setValue(2);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                if (myThread != null) {
                    myThread.setDelay(slider.getValue() * 1000);
                }
            }
        });
        fileText.setText("a b c a d c");
    }

    private double imagePanelWidth = 0;

    private boolean isPanelClose = false;

    private void closeImagePanel() {
        imagePanelWidth = imagePanel.getWidth();
        stage = (Stage) rootPanel.getScene().getWindow();
        stage.setWidth(rightPanel.getWidth());
        imagePanel.setMinWidth(0);
        stage.setX(stage.getX() + imagePanelWidth);
        isPanelClose = true;
        minWidth = minWidth - 300;
    }

    private void openImagePanel() {
        isPanelClose = false;
        imagePanel.setMinWidth(300);
        stage.setWidth(rightPanel.getWidth() + imagePanelWidth);
        stage.setX(stage.getX() - imagePanelWidth);
        minWidth = minWidth + 300;
    }

    @FXML
    private void closeAll(ActionEvent event) {
        System.exit(0);
    }

    private void reloadImage() {
        try {
            lab1.showDirectedGraph(this.g);
        } catch (Exception e) {
            tips.setText("create image failed");
            tips.setStyle("-fx-fill:  #c62828");
        }
        try {
            image.setImage(new Image(new FileInputStream("out.png")));
        } catch (FileNotFoundException e) {
            tips.setText("load image failed");
            tips.setStyle("-fx-fill:  #c62828");
        }
        //reload to biImage 
        if (childController != null) {
            childController.undateImage();
        }
    }

    private void reGenerateGraph() {
        try {
            this.g = lab1.createDirectedGraph("tmp.txt");
        } catch (Exception e) {
        }
        reloadImage();
    }

    private String tempString = "";

    @FXML
    private void runOperation(ActionEvent event) throws Exception {
        resultList.getItems().clear();
        resultText.setText("");
        resultText.setVisible(true);
        tips.setText("");
        tips.setStyle("-fx-fill:  #f44336");
        Integer index = combobox.getSelectionModel().getSelectedIndex();
        String result = "";
        if (this.g == null) {
            tips.setText("please import file or input text to create graph");
            return;
        }
        if (index == 0) {
            String w1 = word1.getText(), w2 = word2.getText();
            if (w1.isEmpty() || w2.isEmpty()) {
                tips.setText("please input two word");
                return;
            }
            try {
                result = lab1.queryBridgeWords(g, w1, w2);
            } catch (Exception e) {
                tips.setText("query bridge words failed");
            }
            resultText.setText(result);
        } else if (index == 1) {
            if (word3.getText().isEmpty()) {
                tips.setText("please input some text");
                return;
            }
            try {
                result = lab1.generateNewText(g, word3.getText());
            } catch (Exception e) {
                tips.setText("generate new text failed");
            }
            resultText.setText("new text generated: \n" + result);
        } else if (index == 2) {
            String w1 = word1.getText(), w2 = word2.getText();
            if (w1.isEmpty() && w2.isEmpty()) {
                tips.setText("please input at least one word");
                return;
            } else if (w1.isEmpty()) {
                String w = w1;
                w1 = w2;
                w2 = w;
            }
            String beforeString = "one shortest path from \"" + w1 + "\" to \"" + w2 + "\": \n";
            try {
                result = lab1.calcShortestPath(g, w1, w2);
            } catch (Exception e) {
                tips.setText("calc shortest path failed");
            }
            String[] results = result.split("\n");
            if (results == null || results[0].isEmpty() || results[0].startsWith("No ")) {
                tempString = "";
                tips.setText("no way fonud");
                resultText.setText(results[0]);
                return;
            }
            resultText.setText(beforeString + results[0]);
            if (!oneShortestWay.isSelected()) {
                resultText.setVisible(false);
            }
            for (String i : results) {
                resultList.getItems().add(resultList.getItems().size() + ". " + i);
            }
            tempString = results[0];
        } else if (index == 3) {
            myThread = new MyThread(g);
            randomWalkThread = new Thread(myThread);
            myThread.setDelay(slider.getValue() * 1000);
            myThread.getResult().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    resultText.setText(t1);
                    g.color(t1, colorMode);
                    reloadImage();
                }
            });

            myThread.getAlivepProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    runButton.setVisible(!t1);
                }
            });

            randomWalkThread.start();

        } else {
            tips.setText("please select an operation");
            return;
        }
        if (index != 3) {
            reloadImage();
        }
        tips.setText("");
        tips.setStyle("-fx-fill:  #212121");
    }

    private int colorMode = 1;
    private Thread randomWalkThread = null;
    private MyThread myThread = null;

    @FXML
    private void comboxboxSelect(ActionEvent event) {
        Integer index = combobox.getSelectionModel().getSelectedIndex();
        wordBox.setVisible(false);
        word1.setVisible(false);
        word2.setVisible(false);
        word3.setVisible(false);
        slider.setVisible(false);
        if (index == 0) {
            wordBox.setVisible(true);
            word1.setVisible(true);
            word2.setVisible(true);
            tips.setText("Please input one or two word");
            tips.setStyle("-fx-fill:  #1565C0");
        } else if (index == 1) {
            word3.setVisible(true);
            tips.setText("Please input some text");
            tips.setStyle("-fx-fill:  #1565C0");
        } else if (index == 2) {
            wordBox.setVisible(true);
            word1.setVisible(true);
            word2.setVisible(true);
            tips.setText("Please input one or two word");
            tips.setStyle("-fx-fill:  #1565C0");
        } else if (index == 3) {
            slider.setVisible(true);
            tips.setText("Click Run to start");
            tips.setStyle("-fx-fill:  #1565C0");
        } else {
            tips.setText("");
            tips.setStyle("-fx-fill: #212121");
        }
    }

    @FXML
    private void fileDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    private void fileDragDropped(DragEvent event) throws FileNotFoundException {
        fileText.setText("");
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            List<File> files = event.getDragboard().getFiles();
            String filePath = files.get(0).getAbsolutePath().toString();
            //accept all file types
            String content = "";
            try {
                content = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                tips.setText("file not exist");
                tips.setStyle("-fx-fill:  #c62828");
            }
            fileText.setText(content);
        }
    }

    @FXML
    private void pressEnterToRun(KeyEvent event) throws Exception {
        if (event.getCode() == KeyCode.ENTER) {
            if (randomWalkThread != null && randomWalkThread.isAlive()) {
                myThread.stop();
            }
            runOperation(new ActionEvent());
        }
    }

    @FXML
    private void imageMouseExited(MouseEvent event) {
        scene = image.getScene();
        scene.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void imageMouseEntered(MouseEvent event) {
        scene = image.getScene();
        scene.setCursor(Cursor.HAND);
    }

    private double imagexOffset = 0, imageyOffset = 0, xOffset = 0, yOffset = 0;
    private BigImageController childController = null;

    @FXML
    private void imageMouseClicked(MouseEvent event) throws IOException, InterruptedException {
        File file = new File("out.png");
        if (systemImageViewer.isSelected() || file.length() > 200 * 1000) {
            //open by system image viewer
            Process p = Runtime.getRuntime().exec("xdg-open out.png");
            p.waitFor();
        } else {
            //open by bigImage
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("bigImage.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            childController = fxmlLoader.getController();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setTitle("imageViewer");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("start.png")));
            ResizeHelper.addResizeListener(stage);
        }
    }

    @FXML
    private void panelMousePressed(MouseEvent event) {
        stage = (Stage) (rootPanel.getScene().getWindow());
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
        startX = stage.getWidth() - xOffset;
        startY = stage.getHeight() - yOffset;
    }

    @FXML
    private void panelMouseDraged(MouseEvent event) {
        Stage stage = (Stage) rootPanel.getScene().getWindow();
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }

    private double border = 14;

    @FXML
    private void rightPanelMouseDraged(MouseEvent event) {

        scene = rightPanel.getScene();
        stage = (Stage) scene.getWindow();
        Cursor cursorEvent = scene.getCursor();
        Boolean isSE = cursorEvent.equals(Cursor.SE_RESIZE);
        if (!isSE) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } else {
            setStageWidth(event.getSceneX() + startX);
            setStageHeight(event.getSceneY() + startY);
        }
    }

    private double maxWidth = Double.MAX_VALUE, maxHeight = Double.MAX_VALUE,
            minWidth = 0, minHeight = 300;

    private void setStageWidth(double width) {
        width = Math.min(width, maxWidth);
        width = Math.max(width, minWidth);
        stage.setWidth(width);
    }

    private void setStageHeight(double height) {
        height = Math.min(height, maxHeight);
        height = Math.max(height, minHeight);
        stage.setHeight(height);
    }

}
