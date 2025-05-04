import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IngestionControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testConnectEndpoint() throws Exception {
    String requestBody = "{\"host\":\"localhost\",\"port\":8123,\"database\":\"default\",\"jwtToken\":\"test\"}";
    mockMvc.perform(post("/api/connect")
        .contentType("application/json")
        .content(requestBody))
        .andExpect(status().isOk());
  }
}