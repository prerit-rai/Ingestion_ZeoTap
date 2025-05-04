import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ClickHouseServiceTest {
  @Autowired
  private ClickHouseService clickHouseService;

  @Test
  public void testFetchTables() {
    List<String> tables = clickHouseService.getTables("localhost", 8123, "default", "jwt_token");
    assertTrue(tables.contains("system.tables")); // Assuming ClickHouse test instance
  }
}