import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.nio.file.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FlatFileServiceTest {
  @Autowired
  private FlatFileService flatFileService;

  @Test
  public void testReadWriteCSV() throws Exception {
    // Write test data
    List<Map<String, Object>> data = new ArrayList<>();
    data.add(Map.of("name", "Alice", "age", 30));
    data.add(Map.of("name", "Bob", "age", 25));
    flatFileService.writeCSV(data, "test.csv");

    // Read and verify
    List<Map<String, String>> records = flatFileService.readCSV("test.csv");
    assertEquals(2, records.size());
    assertEquals("Alice", records.get(0).get("name"));
    
    // Cleanup
    Files.delete(Paths.get("test.csv"));
  }
}