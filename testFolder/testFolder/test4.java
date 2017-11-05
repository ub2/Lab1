package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;
public class test4 {

  @Test
  public void testCalcShortestPath4() throws FileNotFoundException {
    //case 3
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    String path = mainWindow.calcShortestPath(aGraph, "1","2");
    assertEquals("No \"1\" and \"2\" in the graph!", path);
  }

}
