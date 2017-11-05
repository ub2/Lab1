package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class test6 {

  @Test
  public void testCalcShortestPath6() throws FileNotFoundException {
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    //case 2
    String path = mainWindow.calcShortestPath(aGraph, "time","same");
    assertEquals("Inaccessible form \"time\" to \"same\"", path);
  }

}
