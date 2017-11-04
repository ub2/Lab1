import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class Test3 {

  @Test
  public void testCalcShortestPath() {
    mainWindow main = new mainWindow();
    Graph graph=null;
    try {
      graph = main.createDirectedGraph("abc.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals("No \"d\" and \"e\" in the graph!", main.queryBridgeWords(graph, "d", "e"));
  }
}
