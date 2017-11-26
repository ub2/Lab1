package blacktest;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.control;

public class test6 {

  @Test
  public void testCalcShortestPath6() throws FileNotFoundException {
    Graph aGraph = control.createDirectedGraph("noty.txt");
    //case 2
    String path = control.calcShortestPath(aGraph, "time","same");
    assertEquals("Inaccessible form \"time\" to \"same\"", path);
  }

}
