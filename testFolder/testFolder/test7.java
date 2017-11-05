package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class test7 {

  @Test
  public void testCalcShortestPath7() throws FileNotFoundException {
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    //case 2
    String path = mainWindow.calcShortestPath(aGraph,"" , "");
    assertEquals("No \"\" and \"\" in the graph!", path);
  }
}
