import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import lab1.core.Graph;
import lab1.core.mainWindow;

public class Test4 {

  @Test
  public void testCalcShortestPath() {
    mainWindow main = new mainWindow();
    Graph graph=null;
    try {
      graph = main.createDirectedGraph("abc.txt");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    assertEquals("No bridge words from \"a\"to \"b\"!", main.queryBridgeWords(graph, "a", "b"));
  }
}
