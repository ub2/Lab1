package lab1.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javax.naming.spi.DirStateFactory.Result;

import org.omg.CORBA.FloatHolder;

public class mainWindow {

    private static String fileName;
    private static final int infinite = 1000;

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose the input mode: 0 for file, 1 for console");
        //int option = in.nextInt();
        int option = 0;
        if (option == 0) {
            System.out.println("Input file name(suffixed included):");
            do {
                //fileName = in.next();
                fileName = "data.txt";
            } while (!fileName.contains(".txt") && !fileName.contains(".bin"));
        }
        Graph G = createDirectedGraph(fileName);
        //show graph
//		System.out.println("vertex:");
//		for(int i =0;i<G.getVertexNum();i++){
//			System.out.println(G.getNodes().get(i).getWord() + " ");
//		}
//		for(int i =0;i<G.getVertexNum();i++){
//			System.out.println(G.getNodeList().get(i));
//		}

//		//test bridge word
//		String word1, word2;
//		word1 = in.next();word2 = in.next();
//		System.out.println(queryBridgeWords(G, word1, word2));
        //showDirectedGraph(G);
        //test shortest path
//        String w1, w2;
//        //while(true){
//        w1 = in.next();
//        w2 = in.next();
//        String path = calcShortestPath(G, w1, w2);
//        System.out.println(path);
//        String[] Words = path.split("\n");
//        G.color( Words[0], 1);
//        showDirectedGraph(G);
        //}
        //test random walk
        //for (int i = 0; i < 20; i++) {
        System.out.println(randomWalk(G));
        //}

        //test create new text
//		String newText;
//		newText = "Seek to explore new and exciting synergies";
//		newText = generateNewText(G, newText);
//		System.out.println(newText);
    }

    //find bridge word in the sentence
    public static String queryBridgeWords(Graph G, String w1, String w2) {
        String s = null;
        if (G.getNodeSet().containsKey(w1) && G.getNodeSet().containsKey(w2)) {
            s = getBridgeWord(G, w1, w2);
            if (s == null) {
                s = ("No bridge words from \"" + w1 + "\"to \"" + w2 + "\"!");
            } else {
                String[] words = s.split(" ");
                StringBuffer tmp = new StringBuffer();
                for (int i = 0; i < words.length - 1; i++) {
                    tmp.append(words[i] + ", ");
                }

                for (String w : words) {
                    G.color(w1 + " " + w + " " + w2, 1);
                }
                tmp.append(((words.length > 1) ? "and " : " ") + words[words.length - 1] + ".");
                s = "The bridge words from \"" + w1 + "\" to \"" + w2 + "\" " + ((words.length > 1) ? "are:" : "is:") + tmp.toString();
            }
        } else if (G.getNodeSet().containsKey(w2)) {
            s = "No \"" + w1 + "\" in the graph!";
        } else if (G.getNodeSet().containsKey(w1)) {
            s = "No \"" + w2 + "\" in the graph!";
        } else {
            s = "No \"" + w1 + "\" and \"" + w2 + "\" in the graph!";
        }
        return s;
    }

    //create new sentence based on the bridge words
    public static String generateNewText(Graph G, String inputText) {
        //return mode: new sentence do not need modify to the standard format
        String[] words = inputText.replaceAll("[^a-zA-Z]+", " ").replaceFirst("^ ", "").split(" ");
        if (words.length == 0) {
            return "";
        }
        String temp = null;
        String[] bridgeWords;
        StringBuffer newSentence = new StringBuffer();
        Random rand = new Random();
        StringBuffer colorVertex = new StringBuffer();;
        for (int i = 0; i < words.length - 1; i++) {//traverse the new text
            newSentence.append(words[i] + " ");
            temp = getBridgeWord(G, words[i], words[i + 1]);//find bridge words
            if (temp != null) {//if there is any bridge word exists
                bridgeWords = temp.split(" ");
                String w = bridgeWords[rand.nextInt(bridgeWords.length)];
                colorVertex.append(w + " ");
                newSentence.append(w + " ");
            }
        }
        System.out.println(colorVertex.toString());
        G.color(colorVertex.toString(), 0);
        newSentence.append(words[words.length - 1]);
        return newSentence.toString();
    }

    //generate new graph
    public static Graph createDirectedGraph(String filename) throws FileNotFoundException {
        String temp = null;
        if (filename == null) {//read from console
            System.out.println("Input the sentence:");
            Scanner in = new Scanner(System.in);
            temp = in.nextLine();
            in.close();
        } else {//read from file
            temp = new Scanner(new File(filename)).useDelimiter("\\Z").next().toLowerCase();
        }
        String[] words = temp.replaceAll("[^a-zA-Z]+", " ").replaceFirst("^ ", "").split(" ");
        Graph G = new Graph();
        for (int i = 0; i < words.length - 1; i++) {
            G.addNode(words[i]);
            G.addEdge(words[i], words[i + 1]);
        }
        G.addNode(words[words.length - 1]);
        return G;
    }

    //create the graph based on the data
    public static void showDirectedGraph(Graph G) {
        //white black blue blueStart blueEnd
        List<String> color = Arrays.asList("#F5F5F5", "#424242", "#84FFFF", "#82B1FF", "#B9F6CA", "#0091EA");
        GraphViz gv = new GraphViz();
        gv.addln(gv.start_graph());
        Map<String, HashMap<String, Edge>> g = G.getNodeList();
        for (Node a : G.getNodes()) {
            gv.addln(a.getWord() + "[style=filled] " + " [fillcolor =\"" + color.get(a.getColor()) + "\"];");
        }
        for (String i : g.keySet()) {
            HashMap<String, Edge> k = g.get(i);
            for (String j : k.keySet()) {
                gv.addln(i + "->" + j + "[color=\"" + color.get(k.get(j).getColor())
                        + "\",label=\"" + k.get(j).getWeight() + "\"];");
            }
        }
        gv.addln(gv.end_graph());
        String type = "png";
        File out = new File("out." + type);
        gv.writeGraphToFile(gv.getGraph(gv.getDotSource(), type), out);
        G.resetColor();//reset after generate pic
    }

    public static String randomWalk(Graph G) throws IOException, InterruptedException {
        SimpleStringProperty result = null;
        MyThread myThread = new MyThread(G);
        Thread randomWalkThread = new Thread(myThread);
        randomWalkThread.start();
        result = myThread.getResult();
        result.addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                System.out.println(t1);
            }
        });
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String line = "";
        while (line.equalsIgnoreCase("quit") == false) {
            line = in.readLine();
            if (line.equals("p")) {
                System.out.println("paused");
                myThread.pause();
            } else if (line.equals("r")) {
                System.out.println("resumed");
                myThread.resume();
            } else if (line.equals("")) {
                myThread.stop();
                break;
            }
        }
        in.close();
        return result.get();
    }

    public static String calcShortestPath1(Graph G, String word1, String word2) {
        int minDistance = infinite;//the shortest distance
        PriorityQueue<treeNodes> candidate = new PriorityQueue<treeNodes>(G.getVertexNum(), myCompare);
        Map<String, HashMap<String, Edge>> tempMap = G.getNodeList();
        treeNodes prNode = null;
        //initialize the tree
        treeNodes startPoint = new treeNodes(null, 0, word1);
        if (tempMap.get(word1) != null) {
            for (String a : tempMap.get(word1).keySet()) {
                int tempWeight = tempMap.get(word1).get(a).getWeight();
                treeNodes tempNode = new treeNodes(startPoint, tempWeight, a);
                startPoint.getChildren().add(tempNode);
                candidate.add(tempNode);
            }
        }
        //expand the tree 
        while (true) {
            prNode = candidate.poll();
            if (prNode == null || prNode.getWord().equals(word2)) {
                break;
            }
            if (!tempMap.containsKey(prNode.getWord())) {
                candidate.remove(prNode);
                continue;
            }
            for (String b : tempMap.get(prNode.getWord()).keySet()) {
                boolean find = false;
                int newDistance = prNode.getWeiht() + tempMap.get(prNode.getWord()).get(b).getWeight();
                for (treeNodes tNode : candidate) {
                    //if the new distance is shorter then update the tree
                    if (tNode.getWord() == b) {
                        find = true;
                        if (tNode.getWeiht() > newDistance) {
                            candidate.remove(tNode);
                        }
                        candidate.add(new treeNodes(prNode, newDistance, b));
                        break;
                    }
                }
                if (!find) {
                    candidate.add(new treeNodes(prNode, newDistance, b));//add when the node never appear in the tree
                }
            }
        }
        //trace back one route
        if (prNode == null) {
            return ("Inaccessible form \"" + word1 + "\" to \"" + word2 + "\"");
        }
        minDistance = prNode.getWeiht();
        StringBuffer result = new StringBuffer();
        candidate.add(prNode);
        for (treeNodes dNodes : candidate) {
            if (dNodes.getWeiht() == minDistance && dNodes.getWord().equals(word2)) {
                //save route
                prNode = dNodes;
                List<String> tempList = new ArrayList<String>();
                int pathlenth = 0;
                String outWord, inword;
                do {
                    inword = prNode.getWord();
                    tempList.add(inword);
                    prNode = prNode.getFather();
                    outWord = prNode.getWord();
                    pathlenth += tempMap.get(outWord).get(inword).getWeight();

                } while (!prNode.getWord().equals(word1));
                result.append(pathlenth + " " + word1);
                Collections.reverse(tempList);
                for (String aString : tempList) {
                    result.append("->" + aString);
                }
                result.append("\n");
            }
        }
        return result.toString().substring(0, result.length() - 1);
    }

    //calculate the shortest path one to all
    public static String calcShortestPath2(Graph G, String word1) {
        int[] minDistance = new int[G.getVertexNum()];
        int word1Sub = -1, word2Sub = -1;
        Set<Integer> mark = new HashSet<Integer>();
        int[] path = new int[G.getVertexNum()];
        HashMap<String, Edge> tempMap = G.getNodeList().get(word1);
        String tempWord;
        if (tempMap == null)//the word has no connection with others
        {
            return null;
        }
        //initialize all the variable
        for (int i = 0; i < G.getVertexNum(); i++) {
            tempWord = G.getNodes().get(i).getWord();
            if (tempMap.containsKey(tempWord)) {
                minDistance[i] = tempMap.get(tempWord).getWeight();
            } else if (!tempWord.equals(word1)) {
                minDistance[i] = infinite;
            } else {
                word1Sub = i;
                minDistance[i] = infinite;
            }
            path[i] = -1;
            mark.add(i);
        }
        mark.remove(word1Sub);
        for (int i = 0; i < G.getVertexNum() - 1; i++) {//traverse all the vertex and calculate the minimal distance
            //find minimal distance 
            int min = infinite;
            int sub = -1;
            for (int a : mark) {
                if (min > minDistance[a]) {
                    sub = a;
                    min = minDistance[a];
                }
            }
            if (sub == -1) {
                break;
            }
            //remove minimal edge 
            mark.remove(sub);
            //update the distance
            HashMap<String, Edge> localMap = G.getNodeList().get(G.getNodes().get(sub).getWord());
            if (localMap != null) {
                for (int a : mark) {
                    String tWord = G.getNodes().get(a).getWord();
                    int pastDistance = minDistance[a];
                    int newDistance = (localMap.containsKey(tWord)) ? (localMap.get(tWord).getWeight() + min) : (infinite + 1);
                    if (newDistance < pastDistance) {
                        minDistance[a] = newDistance;
                        path[a] = sub;
                    }
                }
            }
        }
        StringBuffer result = new StringBuffer();
        int subscript;
        StringBuffer tmp = new StringBuffer();
        Set<Integer> destination = new HashSet<Integer>();
        for (int i = 0; i < G.getVertexNum(); i++) {
            if (i != word1Sub) {
                destination.add(i);
            }
        }
        for (int a : destination) {
            subscript = a;
            //tmp.setLength(0);
            List<String> tempList = new ArrayList<String>();
            if (minDistance[subscript] != infinite) {
                while (subscript != -1) {
                    tempList.add(G.getNodes().get(subscript).getWord());
                    //tmp.append(G.getNodes().get(subscript).getWord() + "<-");
                    subscript = path[subscript];
                }
                //result.append(tmp + word1);
                result.append(minDistance[a] + " " + word1);
                Collections.reverse(tempList);
                for (String aString : tempList) {
                    result.append("->" + aString);
                }
                result.append("\n");
            }
        }
        return result.toString().substring(0, result.length() - 1);
    }

    public static String calcShortestPath(Graph G, String word1, String word2) {
        //return mode 
        //1. warning message
        //2. w1<-w2<-...(attention! the path is reversed)
        //3. w1<-w2<-... \n w3<-w4 ...
        String result = null;
        if (!G.getNodeSet().containsKey(word1)) {
            if (!G.getNodeSet().containsKey(word2)) {
                result = "No \"" + word1 + "\"" + " and \"" + word2 + "\" in the graph!";
            } else {
                result = "No \"" + word1 + "\" in the graph!";
            }
        } else if (!word2.equals("") && !G.getNodeSet().containsKey(word2)) {
            result = "No \"" + word2 + "\" in the graph!";
        } else {
            if (word2.length() == 0) {
                result = calcShortestPath2(G, word1);
            } else {
                result = calcShortestPath1(G, word1, word2);
            }
            if (result != null) {
                //String r = result.split("\n")[0];
                //G.color(r.substring(r.indexOf(" ")+1), 1);
                G.color(result.split("\n")[0], 1);
            }
        }
        return result;
    }

    public static Comparator<treeNodes> myCompare = new Comparator<treeNodes>() {
        @Override
        public int compare(treeNodes c1, treeNodes c2) {
            return (int) (c1.getWeiht() - c2.getWeiht());
        }
    };

    //get the bridge according to the graph
    public static String getBridgeWord(Graph G, String w1, String w2) {
        ArrayList<String> result = new ArrayList<String>();
        HashMap<String, Edge> e = G.getNodeList().get(w1);
        if (e == null) {
            return null;
        }
        for (String i : e.keySet()) {
            if (G.getNodeList().get(i) != null && G.getNodeList().get(i).containsKey(w2)) {
                result.add(i);
            }
        }
        StringBuffer tmp = new StringBuffer();
        for (String aString : result) {
            tmp.append(aString + " ");
        }
        return (result.size() == 0) ? null : tmp.toString();
    }
}
