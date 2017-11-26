package blacktest;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.control;

public class test1 {

  @Test
  public void testCalcShortestPath1() throws FileNotFoundException {
    Graph aGraph = control.createDirectedGraph("noty.txt");
    //case 1
    String path = control.calcShortestPath(aGraph, "world","the");
    assertEquals("3 world->with->goodness->the\n3 world->will->treat->the", path);
    //fail("Not yet implemented");
  }

}
