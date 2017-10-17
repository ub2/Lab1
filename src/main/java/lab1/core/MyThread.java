
package lab1.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MyThread implements Runnable {

    private volatile StringProperty resultProperty = new SimpleStringProperty(" ");
    private volatile BooleanProperty isAliveProperty = new SimpleBooleanProperty(false);
    private volatile StringBuffer path = new StringBuffer("");
    private volatile Graph G;
    private volatile boolean stop = false;
    private volatile int delay = 1000;
    private volatile boolean suspended = false;

    public MyThread() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public MyThread(Graph G) {
        this.G = G;
        this.mList = G.getNodeList();
    }

    public void stop() {
        this.suspended = false;
        this.stop = true;
        this.isAliveProperty.set(false);
    }

    public void pause() throws InterruptedException {
        suspended = true;
    }

    private void waitWhileSuspended() throws InterruptedException {
        while (suspended) {
            Thread.sleep(50);
        }
    }

    public void resume() {
        suspended = false;
    }

    public void setDelay(double s) {
        if (s < 200) {
            this.delay = 200;
        } else {
            this.delay = (int) s;
        }
    }

    @Override
    public void run() {
        isAliveProperty.set(true);
        Random random = new Random();
        String presentWord = G.getNodes().get(random.nextInt(G.getVertexNum())).getWord();//get first word
        ArrayList<Edge> edges = new ArrayList<>();
        String nextWord;
        Edge tempEdge;
        path.append(presentWord);
        resultProperty.set(path.toString());
        try {
            Thread.sleep(this.delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
            isAliveProperty.set(false);
        }
        try {
            waitWhileSuspended();
        } catch (InterruptedException ex) {
            Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (!this.stop) {
            nextWord = moveOneStep(G, presentWord);
            if (nextWord != null) {
                tempEdge = G.getNodeList().get(presentWord).get(nextWord);
                presentWord = nextWord;
                path.append("->" + presentWord);
                resultProperty.set(path.toString());
                if (!edges.contains(tempEdge)) {
                    edges.add(tempEdge);
                } else {
                    break;
                }
            } else {
                break;
            }
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
                isAliveProperty.set(false);
            }
            try {
                waitWhileSuspended();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        isAliveProperty.set(false);
    }

    public SimpleStringProperty getResult() {
        return (SimpleStringProperty) this.resultProperty;
    }

    public SimpleBooleanProperty getAlivepProperty() {
        return (SimpleBooleanProperty) this.isAliveProperty;
    }

    private Map<String, HashMap<String, Edge>> mList;
    //add private mList for CPU optimization, initialized with getNodeList();
    public String moveOneStep(Graph G, String presentWord) {
        Random random = new Random();
        HashMap<String, Edge> map = mList.get(presentWord);
        if (map == null) {
            return null; //if there is no road then exit
        }
        String[] tempList = map.keySet().toArray(new String[0]);
        return tempList[random.nextInt(tempList.length)];
    }

};
