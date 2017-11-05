package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class test9 {

  @Test
  public void testCalcShortestPath8() throws FileNotFoundException {
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    //case 2
    String path = mainWindow.calcShortestPath(aGraph, "same", "time");
    assertEquals("1 same->time", path);
  }

}
