# Daymark

Daymark is a task-management REST API built with Java 17 and Spring Boot. It uses a layered structure and persists data through Spring Data JPA (H2 for local development).

## Requirements

- Java 17 or newer
- Maven 3.6.3 or newer

## Run

```powershell
mvn spring-boot:run
```

The API listens on `http://localhost:8080`.

## Architecture

- `controller`: HTTP endpoints
- `dto.request` / `dto.response`: API request and response contracts
- `service` / `service.impl`: application use cases and transaction boundaries
- `repository`: Spring Data persistence interface
- `entity`: JPA entity and persistence lifecycle
- `mapper`: mapping between API DTOs and domain entities
- `common.exception`: consistent API error responses using the shared response envelope
- `PageResponse`: stable API-owned pagination metadata, independent of Spring Data's internal page serialization

## API

| Method | Path | Description |
| --- | --- | --- |
| `GET` | `/api/v1/tasks` | List tasks; supports `page`, `size`, `sort`, and optional `completed` |
| `GET` | `/api/v1/tasks/overdue` | List incomplete tasks past their due date |
| `GET` | `/api/v1/tasks/{id}` | Get a task |
| `POST` | `/api/v1/tasks` | Create a task |
| `PUT` | `/api/v1/tasks/{id}` | Replace task fields |
| `PATCH` | `/api/v1/tasks/{id}/completion?completed=true` | Update completion state |
| `DELETE` | `/api/v1/tasks/{id}` | Delete a task |

Create a task with JSON:

```json
{
  "title": "Review pull requests",
  "description": "Check the Codzee feedback",
  "dueDate": "2027-01-15",
  "priority": "HIGH"
}
```

The local H2 database is in memory and resets when the application restarts. Replace the datasource configuration for a persistent production database.

## Response format

Successful and failed requests use the same response envelope. A successful task response looks like:

```json
{
  "success": true,
  "code": "SUCCESS",
  "message": "Task retrieved successfully",
  "data": {
    "id": 1,
    "title": "Review pull requests",
    "description": "Check the Codzee feedback",
    "completed": false,
    "createdAt": "2026-10-19T10:00:00Z",
    "updatedAt": "2026-10-19T10:00:00Z",
    "version": 0
  },
  "errors": {},
  "timestamp": "2026-10-19T10:00:00Z"
}
```

Validation errors return HTTP `400` with field-level details in `errors`; missing tasks return `404`; conflicting updates return `409`. Unexpected server errors return a generic message so internal exception details are not exposed to API clients.

