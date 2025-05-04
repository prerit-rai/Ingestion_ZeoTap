import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IngestionController {
    @PostMapping("/connect")
    public ResponseEntity<?> connect(
        @RequestBody ClickHouseConfig config
    ) {
        try {
            ClickHouseService service = new ClickHouseService(
                config.getHost(), config.getPort(), config.getDatabase(), config.getJwtToken()
            );
            return ResponseEntity.ok(service.fetchTables());
        } catch (SQLException e) {
            return ResponseEntity.status(500).body("Connection failed: " + e.getMessage());
        }
    }

    @PostMapping("/ingest")
    public ResponseEntity<?> ingest(
        @RequestBody IngestionRequest request
    ) {
        try {
            if ("ClickHouse".equals(request.getSourceType())) {
                // ClickHouse → CSV
                ClickHouseService chService = new ClickHouseService(
                    request.getHost(), request.getPort(), request.getDatabase(), request.getJwtToken()
                );
                List<Map<String, Object>> data = chService.readData(request.getTable(), request.getColumns());
                new FlatFileService().writeCSV(data, request.getOutputPath());
            } else {
                // CSV → ClickHouse
                List<Map<String, String>> csvData = new FlatFileService().readCSV(request.getInputPath());
                // Convert data types and write to ClickHouse (implementation omitted)
            }
            return ResponseEntity.ok(Map.of("recordCount", data.size()));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Ingestion failed: " + e.getMessage());
        }
    }
}

// DTO Classes
class ClickHouseConfig {
    private String host;
    private int port;
    private String database;
    private String jwtToken;
    // Getters & Setters
}

class IngestionRequest {
    private String sourceType;
    private String host;
    private int port;
    private String database;
    private String jwtToken;
    private String table;
    private List<String> columns;
    private String inputPath;
    private String outputPath;
    // Getters & Setters
}