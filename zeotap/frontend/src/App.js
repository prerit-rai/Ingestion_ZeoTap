import React, { useState } from 'react';
import axios from 'axios';
import ConnectionForm from './components/ConnectionForm';
import ColumnSelector from './components/ColumnSelector';

function App() {
  const [tables, setTables] = useState([]);
  const [selectedTable, setSelectedTable] = useState('');
  const [selectedColumns, setSelectedColumns] = useState([]);
  const [status, setStatus] = useState('');

  const handleConnect = async (config) => {
    try {
      const response = await axios.post('/api/connect', config);
      setTables(response.data);
      setStatus('Connected successfully');
    } catch (error) {
      setStatus('Connection failed: ' + error.message);
    }
  };

  const handleIngest = async () => {
    try {
      const response = await axios.post('/api/ingest', {
        sourceType: 'ClickHouse',
        host: 'localhost',
        port: 8443,
        database: 'default',
        jwtToken: 'your_jwt_here',
        table: selectedTable,
        columns: selectedColumns,
        outputPath: 'output.csv'
      });
      setStatus(`Ingested ${response.data.recordCount} records`);
    } catch (error) {
      setStatus('Ingestion failed: ' + error.message);
    }
  };

  return (
    <div className="App">
      <h1>Data Ingestion Tool</h1>
      <ConnectionForm onConnect={handleConnect} />
      {tables.length > 0 && (
        <div>
          <select onChange={(e) => setSelectedTable(e.target.value)}>
            <option value="">Select a Table</option>
            {tables.map((table) => (
              <option key={table} value={table}>{table}</option>
            ))}
          </select>
          <ColumnSelector
            columns={selectedTable ? [] : []} // Fetch columns dynamically here
            selectedColumns={selectedColumns}
            onSelect={setSelectedColumns}
          />
          <button onClick={handleIngest}>Start Ingestion</button>
        </div>
      )}
      <div>{status}</div>
    </div>
  );
}

export default App;