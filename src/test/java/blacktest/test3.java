package blacktest;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class test3 {

  @Test
  public void testCalcShortestPath3() throws FileNotFoundException {
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    //case 2
    String path = mainWindow.calcShortestPath(aGraph, "to","aksjsad");
    assertEquals("No \"aksjsad\" in the graph!", path);
  }

}
