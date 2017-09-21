package lab1.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {

    private int vertexNum;//the number of the vertex
    private int edgeNum;// the number of the edge that exist in the graph
    private Map<String, HashMap<String, Edge>> nodeList = new HashMap<String, HashMap<String, Edge>>();
    //private ArrayList<String> nodeSet =new ArrayList<String>();
    private HashMap<String, Integer> nodeSet = new HashMap<String, Integer>();
    private ArrayList<Node> nodes = new ArrayList<Node>();

    public Graph() {
        vertexNum = 0;
        edgeNum = 0;
    }

    public void setVertexNum(int num) {
        vertexNum = num;
    }

    public int getVertexNum() {
        return vertexNum;
    }

    public void setEdgeNum(int num) {
        edgeNum = num;
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    //add edge if the edge not exist else no operation
    public void addEdge(String v1, String v2) {//i,j represent the two vertex's subscript respectively
        if (!nodeList.containsKey(v1))//if v1 not exists
        {
            nodeList.put(v1, new HashMap<String, Edge>());
        }
        if (!nodeList.get(v1).containsKey(v2)) {
            nodeList.get(v1).put(v2, new Edge());//if edge not exists
        }
        Edge tmp = nodeList.get(v1).get(v2);
        tmp.setWeight(tmp.getWeight() + 1);
        if (tmp.getWeight() == 1)//add the sum of the edge
        {
            edgeNum++;
        }
    }

    public Map<String, HashMap<String, Edge>> getNodeList() {
        return nodeList;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void addNode(String word) {
        if (!nodeSet.containsKey(word)) {//need to be improved
            nodes.add(new Node(word));
            nodeSet.put(word, vertexNum++);
        }
    }

    public void resetColor() {
        for (String a : nodeList.keySet()) {
            for (Edge b : nodeList.get(a).values()) {
                b.setColor(1);
            }
        }
        for (Node a : nodes) {
            a.setColor(0);
        }
    }

    public HashMap<String, Integer> getNodeSet() {
        return nodeSet;
    }
    
    public void color(String path, int mode) {
        if (path == null || path.isEmpty()) {
            return;
        }
        String[] words = path.split("->| |\n");
        if (mode == 0) {//just vertexs
            for (String aString : words) {
                colorVertex( aString, 2);
            }
        } else {//start ->paths ->end
            int len = words.length;
            for (int i = 0; i < len - 1; i++) {
                if (nodeList.get(words[i]) != null && nodeList.get(words[i]).get(words[i + 1]) != null) {
                    nodeList.get(words[i]).get(words[i + 1]).setColor(5);
                }
                colorVertex(words[i + 1], 2);
            }
            //起点覆盖终点
            colorVertex( words[len - 1], 3);
            colorVertex( words[0], 4);
        }
    }

    public void colorVertex(String word, int color) {
        if (word != null && !word.isEmpty() && nodeSet.get(word) != null) {
            nodes.get(nodeSet.get(word)).setColor(color);
        }
    }
}
