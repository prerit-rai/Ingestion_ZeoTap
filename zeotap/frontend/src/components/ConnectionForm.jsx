import React, { useState } from 'react';

const ConnectionForm = ({ onConnect }) => {
  const [host, setHost] = useState('localhost');
  const [port, setPort] = useState(8443);
  const [database, setDatabase] = useState('default');
  const [jwtToken, setJwtToken] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    onConnect({ host, port, database, jwtToken });
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="text" value={host} onChange={(e) => setHost(e.target.value)} placeholder="Host" required />
      <input type="number" value={port} onChange={(e) => setPort(e.target.value)} placeholder="Port" required />
      <input type="text" value={database} onChange={(e) => setDatabase(e.target.value)} placeholder="Database" required />
      <input type="password" value={jwtToken} onChange={(e) => setJwtToken(e.target.value)} placeholder="JWT Token" required />
      <button type="submit">Connect to ClickHouse</button>
    </form>
  );
};

export default ConnectionForm;