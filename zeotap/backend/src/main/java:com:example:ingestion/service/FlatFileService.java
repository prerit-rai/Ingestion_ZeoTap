import org.apache.commons.csv.*;
import java.nio.file.*;
import java.util.*;

public class FlatFileService {
    // Read CSV file
    public List<Map<String, String>> readCSV(String filePath) throws Exception {
        List<Map<String, String>> records = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));
             CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader())) {
            for (CSVRecord record : parser) {
                Map<String, String> row = new HashMap<>();
                for (String header : parser.getHeaderMap().keySet()) {
                    row.put(header, record.get(header));
                }
                records.add(row);
            }
        }
        return records;
    }

    // Write data to CSV
    public void writeCSV(List<Map<String, Object>> data, String outputPath) throws Exception {
        if (data.isEmpty()) return;
        Set<String> headers = data.get(0).keySet();
        try (CSVPrinter printer = new CSVPrinter(
            Files.newBufferedWriter(Paths.get(outputPath)),
            CSVFormat.DEFAULT.withHeader(headers.toArray(new String[0])))
        {
            for (Map<String, Object> row : data) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(row.get(header).toString());
                }
                printer.printRecord(values);
            }
        }
    }
}