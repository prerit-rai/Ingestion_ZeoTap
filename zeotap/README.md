# ClickHouse ↔ Flat File Ingestion Tool

A web app for bidirectional data transfer between ClickHouse and CSV files.

## Features
- JWT authentication for ClickHouse
- Column selection UI
- Record count validation

## Setup

### Prerequisites
- Docker
- Java 17, Maven
- Node.js 18+

### Run with Docker
```bash
docker-compose up --build