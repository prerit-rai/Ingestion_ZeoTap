import java.sql.*;
import java.util.*;

public class ClickHouseService {
    private final String connectionUrl;

    public ClickHouseService(String host, int port, String database, String jwtToken) {
        this.connectionUrl = String.format(
            "jdbc:clickhouse://%s:%d/%s?ssl=true&jwt=%s",
            host, port, database, jwtToken
        );
    }

    // Fetch list of tables
    public List<String> fetchTables() throws SQLException {
        List<String> tables = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl)) {
            DatabaseMetaData meta = conn.getMetaData();
            ResultSet rs = meta.getTables(null, null, "%", new String[]{"TABLE"});
            while (rs.next()) tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }

    // Read data from a table (with selected columns)
    public List<Map<String, Object>> readData(String table, List<String> columns) {
        String query = String.format("SELECT %s FROM %s", String.join(", ", columns), table);
        List<Map<String, Object>> data = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            ResultSetMetaData meta = rs.getMetaData();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    row.put(meta.getColumnName(i), rs.getObject(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    // Write data to ClickHouse
    public void writeData(String table, List<Map<String, Object>> data) {
        // Implement batch INSERT logic (omitted for brevity)
    }
}