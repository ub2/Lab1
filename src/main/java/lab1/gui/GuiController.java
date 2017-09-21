package lab1.gui;

import lab1.core.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.effects.JFXDepthManager;
import com.sun.corba.se.spi.oa.OADefault;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIcon;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lab1.lemmatization.StanfordLemmatizer;

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
    @FXML
    private ContextMenu rightClickMenu;
    
    @FXML
    private void clickToRun(MouseEvent event) throws Exception {
        runOperation();
    }
    
    private class getStage implements Runnable {
        
        @Override
        public void run() {
            while (scene == null || stage == null) {
                try {
                    scene = rootPanel.getScene();
                    stage = (Stage) scene.getWindow();
                } catch (Exception e) {
                }
            }
        }
        
    }
    
    private class nlpThread implements Runnable {
        
        @Override
        public void run() {
            
            StanfordLemmatizer slem = new StanfordLemmatizer();
            final String result = (String.join(" ", slem.lemmatize(fileText.getText().replaceAll("[^a-zA-Z]+", " "))));
            Platform.runLater(() -> {
                fileText.setText(result);
                tips.setText("");
            });
        }
    }
    
    private class queryBridgeWords implements Runnable {
        
        @Override
        public void run() {
            String w1 = word1.getText(), w2 = word2.getText();
            if (w1.isEmpty() || w2.isEmpty()) {
                Platform.runLater(() -> {
                    tips.setText("please input two word");
                });
                return;
            }
            try {
                final String result = lab1.queryBridgeWords(g, w1, w2);
                Platform.runLater(() -> {
                    resultText.setText(result);
                });
                reloadImage();
                
            } catch (Exception e) {
                Platform.runLater(() -> {
                    tips.setText("query bridge words failed");
                });
            }
            
        }
    }

    private class generateNewText implements Runnable {
        
        @Override
        public void run() {
            String w3 = word3.getText();
           // System.out.println(w3);
            if (w3.isEmpty()) {
                Platform.runLater(() -> {
                    tips.setText("please input some text");
                });
                return;
            } else {
                try {
                    final String result = lab1.generateNewText(g, w3);
                    Platform.runLater(() -> {
                        resultText.setText("new text generated: \n" + result);
                    });
                    reloadImage();

                } catch (Exception e) {
                    Platform.runLater(() -> {
                        tips.setText("generate new text failed");
                    });
                }
            }
        }
    }
    
    private class calcShortestPath implements Runnable {
        
        @Override
        public void run() {
            String w1 = word1.getText(), w2 = word2.getText();
                if (w1.isEmpty() && w2.isEmpty()) {
                Platform.runLater(() -> {
                    tips.setText("please input at least one word");
                });
                return;
            } else if (w1.isEmpty()) {
                String w = w1;
                w1 = w2;
                w2 = w;
            }
            String beforeString = "one shortest path from \"" + w1 + "\" to \"" + w2 + "\": \n";
            try {
                final String result = lab1.calcShortestPath(g, w1, w2);
                final String[] results = result.split("\n");
                if (results.length == 0 || results[0].isEmpty() || results[0].startsWith("No ")) {
                    tempString = "";
                    Platform.runLater(() -> {
                        tips.setText("no way fonud");
                    });
                    return;
                }
                Platform.runLater(() -> {
                    resultText.setText(beforeString + results[0]);
                    if (!oneShortestWay.isSelected()) {
                        resultText.setVisible(false);
                    }
                    resultList.getItems().clear();
                    for (String i : results) {
                        resultList.getItems().add(resultList.getItems().size() + ". " + i);
                    }
                    resultList.getSelectionModel().selectFirst();
                });
                //reloadImage();
                tempString = results[0];
            } catch (Exception e) {
                Platform.runLater(() -> {
                    tips.setText("calc shortest path failed");
                });
            }
        }
    }
    
    private class inputThread implements Runnable {
        
        private String newValue = "";
        
        public inputThread(String s) {
            super();
            this.newValue = s;
        }
        
        @Override
        public void run() {
            try (PrintWriter out = new PrintWriter("tmp.txt")) {
                out.println(newValue);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
            }
            reGenerateGraph();
        }
    }
    
    private class reloadImageThread implements Runnable {
        
        Graph g = null;
        
        public reloadImageThread(Graph g) {
            super();
            this.g = g;
        }
        
        @Override
        public void run() {
            try {
                lab1.showDirectedGraph(g);
                final Image img = new Image(new FileInputStream("out.png"));
                Platform.runLater(() -> {
                    image.setImage(img);
                });
            } catch (Exception e) {
                tips.setText("create image failed");
                tips.setStyle("-fx-fill:  #c62828");
            }
            //reload to biImage 
            if (childController != null) {
                childController.updateImage();
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
//                Pattern p = Pattern.compile("\\d (.+)");
//                Matcher m = p.matcher(newValue);
//                if (m.find()) {
//                    // System.out.println(m.group(1));

//                    g.color(m.group(1), 1);
                g.color(newValue, 1);
                reloadImage();
//                }
            }
        });
        
        word1.setVisible(false);
        word2.setVisible(false);
        word3.setVisible(false);
        wordBox.setVisible(false);
        lab1 = new mainWindow();
        g = null;
        //press enter
        rightPanel.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
            if (!e.getTarget().equals(fileText) && KeyCode.ENTER.equals(e.getCode())) {
                try {
                    runOperation();
                } catch (Exception ex) {
                    Logger.getLogger(GuiController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //fileText
        fileText.textProperty().addListener((observable, oldValue, newValue) -> {
            //tips.setText("");
            if (myThread != null) {
                myThread.stop();
            }
            new Thread(new inputThread(newValue)).start();
        });
        
        JFXDepthManager.setDepth(settingPanel, 1);

        //rightClickMenu
        MenuItem lemmatization = new MenuItem("lemmatization by stanford nlp");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        rightClickMenu.getItems().clear();
        rightClickMenu.getItems().addAll(lemmatization);
        lemmatization.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tips.setText("processing lemmatization... ");
                tips.setStyle("-fx-fill: #FF5722");
                new Thread(new nlpThread()).start();
            }
        });

        //resize
        rightPanel.addEventHandler(MouseEvent.MOUSE_MOVED, e -> {
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
                Logger.getLogger(GuiController.class
                        .getName()).log(Level.SEVERE, null, ex);
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
        hideLeftPanel.selectedProperty().addListener((ov, t, t1) -> {
            //stage.hide();
            //scene
            if (hideLeftPanel.isSelected()) {
                closeImagePanel();
            } else {
                openImagePanel();
            }
            //stage.show();
        });
        
        oneShortestWay.addEventFilter(ActionEvent.ACTION, e -> {
            if (currentIndex != 2 || tempIndex != 2) {
                return;
            }
            if (oneShortestWay.isSelected()) {
                resultText.setVisible(true);
                resultList.getSelectionModel().selectFirst();
                g.color(tempString, colorMode);
            } else if (true) {
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
                    Logger.getLogger(GuiController.class
                            .getName()).log(Level.SEVERE, null, ex);
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
                    myThread.setDelay(5000 / slider.getValue());
                }
            }
        });
        //fileText.setText("a b c a d c");
        new Thread(new getStage()).start();
    }
    
    private double imagePanelWidth = 0;
    
    private boolean isPanelClose = false;
    
    private void closeImagePanel() {
        imagePanelWidth = imagePanel.getWidth();
        stage.setWidth(rightPanel.getWidth());
        imagePanel.setMinWidth(0);
        //stage.setX(stage.getX() + imagePanelWidth);
        isPanelClose = true;
        minWidth = minWidth - 300;
    }
    
    private void openImagePanel() {
        isPanelClose = false;
        imagePanel.setMinWidth(300);
        stage.setWidth(rightPanel.getWidth() + imagePanelWidth);
        //stage.setX(stage.getX() - imagePanelWidth);
        minWidth = minWidth + 300;
    }
    
    @FXML
    private void closeAll(ActionEvent event) {
        System.exit(0);
    }
    
    private final void reloadImage() {
        new Thread(new reloadImageThread(this.g)).start();
    }
    
    private void reGenerateGraph() {
        try {
            this.g = lab1.createDirectedGraph("tmp.txt");
        } catch (Exception e) {
        }
        reloadImage();
    }
    
    private String tempString = "";
    private int tempIndex = -1;
    
    private void runOperation() throws Exception {
        //reloadImage();
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
        tempIndex = index;
        if (index == 0) {
            new Thread(new queryBridgeWords()).start();
        } else if (index == 1) {
            new Thread(new generateNewText()).start();
        } else if (index == 2) {
            new Thread(new calcShortestPath()).start();
        } else if (index == 3) {
            myThread = new MyThread(g);
            randomWalkThread = new Thread(myThread);
            myThread.setDelay(5000 / slider.getValue());
            myThread.getResult().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                    Platform.runLater(() -> {
                        resultText.setText(t1);
                    });
                    g.color(t1, colorMode);
                    reloadImage();
                }
            });
            myThread.getAlivepProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    Platform.runLater(() -> {
                        runButton.setVisible(!t1);
                    });
                }
            });
            randomWalkThread.start();
        } else {
            tips.setText("please select an operation");
            return;
        }
    }
    
    private int colorMode = 1;
    private Thread randomWalkThread = null;
    private MyThread myThread = null;
    private int currentIndex = -1;
    
    @FXML
    private void comboxboxSelect(ActionEvent event) {
        Integer index = combobox.getSelectionModel().getSelectedIndex();
        currentIndex = index;
        
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
            tips.setText("slide to change speed");
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
                Logger.getLogger(GuiController.class
                        .getName()).log(Level.SEVERE, null, ex);
                tips.setText("file not exist");
                tips.setStyle("-fx-fill:  #c62828");
            }
            fileText.setText(content);
        }
    }
    
    @FXML
    private void imageMouseExited(MouseEvent event) {
        scene.setCursor(Cursor.DEFAULT);
    }
    
    @FXML
    private void imageMouseEntered(MouseEvent event) {
        scene.setCursor(Cursor.HAND);
    }
    
    private double imagexOffset = 0, imageyOffset = 0, xOffset = 0, yOffset = 0;
    private BigImageController childController = null;
    
    @FXML
    private void imageMouseClicked(MouseEvent event) throws IOException, InterruptedException {
        File file = new File("out.png");
        if (systemImageViewer.isSelected() || file.length() > 500 * 1024) {
            //open by system image viewer
            Process p = Runtime.getRuntime().exec("xdg-open out.png");
            p.waitFor();
        } else {
            //open by bigImage
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/bigImage.fxml"));
            Parent parent = (Parent) fxmlLoader.load();
            childController = fxmlLoader.getController();
            Stage stage = new Stage(StageStyle.UNDECORATED);
            stage.setScene(new Scene(parent));
            stage.setTitle("imageViewer");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/start.png")));
            childController.updateImage();
            stage.setX(Screen.getPrimary().getBounds().getWidth() / 2.2);
            stage.setY(50);
            this.stage = (Stage) image.getScene().getWindow();
            stage.showingProperty().addListener((ov, t, t1) -> {
                if (!t1) {
                    hideLeftPanel.setSelected(false);
                    //openImagePanel();
                } else {
                    hideLeftPanel.setSelected(true);
                }
            });
            stage.show();
            this.stage.setX(stage.getX() - rightPanel.getWidth() - 50);
        }
    }
    
    private double startX = 0, startY = 0;
    
    @FXML
    private void panelMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
        startX = stage.getWidth() - xOffset;
        startY = stage.getHeight() - yOffset;
    }
    
    @FXML
    private void panelMouseDraged(MouseEvent event) {
        stage.setX(event.getScreenX() - xOffset);
        stage.setY(event.getScreenY() - yOffset);
    }
    
    private double border = 14;
    
    @FXML
    private void rightPanelMouseDraged(MouseEvent event) {
        Boolean isSE = scene.getCursor().equals(Cursor.SE_RESIZE);
        if (!isSE) {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        } else {
            setStageWidth(event.getSceneX() + startX);
            setStageHeight(event.getSceneY() + startY);
        }
    }
    
    private double maxWidth = Double.MAX_VALUE, maxHeight = Double.MAX_VALUE,
            minWidth = 700, minHeight = 500;
    
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
