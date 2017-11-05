package testFolder;

import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;



public class test5 {

  @Test
  public void testCalcShortestPath5() throws FileNotFoundException {
    //case 3
    Graph aGraph = mainWindow.createDirectedGraph("noty.txt");
    String path = mainWindow.calcShortestPath(aGraph, "with","");
    assertEquals("6 with->goodness->the->world->will->treat\n" + 
        "2 with->goodness->the\n" + 
        "4 with->goodness->the->world\n" + 
        "1 with->goodness\n" + 
        "5 with->goodness->the->world->will\n" + 
        "7 with->goodness->the->world->will->treat->you\n" + 
        "8 with->goodness->the->world->will->treat->you->well\n" + 
        "9 with->goodness->the->world->will->treat->you->well->as\n" + 
        "3 with->goodness->the->same\n" + 
        "4 with->goodness->the->same->time", path);
  }

}
