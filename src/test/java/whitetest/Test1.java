package whitetest;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.control;

public class Test1 {

  @Test
  public void testCalcShortestPath() {
    control main = new control();
    Graph graph=null;
    try {
      graph = main.createDirectedGraph("abc.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals("No \"d\" in the graph!", main.queryBridgeWords(graph, "d", "c"));
  }
}
