package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class test8 {

  @Test
  public void testCalcShortestPath8() throws FileNotFoundException {
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    //case 2
    String path = mainWindow.calcShortestPath(aGraph, "12", "");
    assertEquals("No \"12\" and \"\" in the graph!", path);
  }

}
