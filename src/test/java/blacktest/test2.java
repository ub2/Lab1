package blacktest;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.control;

public class test2 {

  @Test
  public void testCalcShortestPath2() throws FileNotFoundException {
    Graph aGraph = control.createDirectedGraph("noty.txt");
    //case 2
    String path = control.calcShortestPath(aGraph, "1world","the");
    assertEquals("No \"1world\" in the graph!", path);
  }

}
