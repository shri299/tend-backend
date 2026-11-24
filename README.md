# Tend backend

Java 21 / Spring Boot API for **Tend**, a private, kind personal-life companion. This repository contains the modular-monolith backend, PostgreSQL migrations, authentication, validation, OpenAPI documentation, and Docker development environment.

## Implemented Phase 1 API

- JWT registration and login with BCrypt password hashing
- Per-user, customizable life categories with eight defaults
- Brain-dump entries, goals, tasks/subtasks, and flexible habits
- Optional due/scheduled dates, energy needs, and minimum viable actions
- Mood/energy/stress check-ins
- Weekly priorities, notes, and reflection
- Deterministic energy-based dashboard suggestion and descriptive counts
- Schedule-aware monthly habit logs with idempotent updates and gentle status semantics
- Task postponement history, persistent focus sessions, goal milestones/progress, and range summaries
- Validation, global error responses, Flyway constraints/indexes, and Swagger UI

All reads and writes derive the user ID from the verified token. Synthetic data only is used in tests.

## Local development

Requirements: Docker Desktop, or Java 21 + Maven 3.9 + PostgreSQL 16.

```bash
cp .env.example .env
docker compose up --build
```

API: `http://localhost:8080/api`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

For a host JVM workflow, start only PostgreSQL with `docker compose up db -d`, then run `mvn spring-boot:run`. Run tests with `mvn verify`.

## Security notes

Set a strong random `JWT_SECRET` outside local development. Credentials and health/journal content are never logged. Production deployment should add TLS, token rotation/revocation, authentication rate limiting through a durable store or gateway, private object storage for future attachments, and a formal backup/deletion policy.

See [architecture](docs/ARCHITECTURE.md), [schema](docs/SCHEMA.md), [Phase 1.1 APIs](docs/PHASE_1_1_API.md), and [roadmap](ROADMAP.md).
