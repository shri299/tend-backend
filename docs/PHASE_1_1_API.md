# Phase 1.1 API

All endpoints require the authenticated owner. Local Docker demo mode deliberately maps requests to one synthetic user; it is disabled by default outside Compose.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/habits/calendar?month=YYYY-MM` | Habits, logs, schedule-aware monthly metrics |
| PUT/DELETE | `/api/habits/{habitId}/logs/{date}` | Idempotently record or clear a day |
| POST | `/api/tasks/{id}/postpone` | Move a task and append optional reason/stage history |
| GET | `/api/tasks/{id}/postponements` | Owned postponement timeline |
| POST | `/api/focus-sessions` | Start one active session per user |
| GET | `/api/focus-sessions/active` | Restore a timer after refresh |
| POST | `/api/focus-sessions/{id}/pause` | Accumulate elapsed time and pause |
| POST | `/api/focus-sessions/{id}/resume` | Resume timing |
| POST | `/api/focus-sessions/{id}/finish` | Save outcome and optional reflection |
| GET | `/api/focus-sessions?days=7` | Focus history |
| GET | `/api/goals/{id}/progress` | Milestones, timeline, calculated progress |
| POST | `/api/goals/{id}/milestones` | Add milestone |
| PATCH | `/api/goals/{goalId}/milestones/{id}` | Toggle milestone |
| POST | `/api/goals/{id}/progress` | Add manual progress timeline entry |
| GET/PUT | `/api/preferences` | Low-energy and optional-streak preferences |
| GET | `/api/analytics/summary?from=&to=` | Deterministic range summary |

Habit states are `COMPLETED`, `PARTIAL`, `SKIPPED`, and `MISSED`. Absence of a log remains **not recorded**, never an inferred failure.
