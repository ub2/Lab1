package blacktest;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.control;
public class test4 {

  @Test
  public void testCalcShortestPath4() throws FileNotFoundException {
    //case 3
    Graph aGraph = control.createDirectedGraph("noty.txt");
    String path = control.calcShortestPath(aGraph, "1","2");
    assertEquals("No \"1\" and \"2\" in the graph!", path);
  }

}
