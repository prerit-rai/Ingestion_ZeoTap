import React from 'react';

const ColumnSelector = ({ columns, selectedColumns, onSelect }) => {
  const handleCheckboxChange = (column) => {
    const updated = selectedColumns.includes(column)
      ? selectedColumns.filter(c => c !== column)
      : [...selectedColumns, column];
    onSelect(updated);
  };

  return (
    <div>
      <h3>Select Columns</h3>
      {columns.map((column) => (
        <label key={column}>
          <input
            type="checkbox"
            checked={selectedColumns.includes(column)}
            onChange={() => handleCheckboxChange(column)}
          />
          {column}
        </label>
      ))}
    </div>
  );
};

export default ColumnSelector;