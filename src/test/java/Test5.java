import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class Test5 {

  @Test
  public void testCalcShortestPath() {
    mainWindow main = new mainWindow();
    Graph graph=null;
    try {
      graph = main.createDirectedGraph("abcd.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals("The bridge words from \"a\" to \"c\" are:b, and d.", main.queryBridgeWords(graph, "a", "c"));
  }
}
