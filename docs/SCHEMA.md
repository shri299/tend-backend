# Database schema

```mermaid
erDiagram
  APP_USER ||--o{ LIFE_CATEGORY : owns
  APP_USER ||--o{ LIFE_ITEM : owns
  APP_USER ||--o{ WELLBEING_CHECKIN : records
  APP_USER ||--o{ WEEKLY_PLAN : creates
  LIFE_CATEGORY o|--o{ LIFE_ITEM : groups
  LIFE_ITEM o|--o{ LIFE_ITEM : parent_of
  LIFE_ITEM ||--o{ HABIT_COMPLETION : records
  LIFE_ITEM ||--o{ TASK_POSTPONEMENT : postpones
  LIFE_ITEM ||--o{ FOCUS_SESSION : focuses_on
  LIFE_ITEM ||--o{ GOAL_MILESTONE : breaks_into
  LIFE_ITEM ||--o{ GOAL_PROGRESS : progresses
  APP_USER ||--o{ HABIT_COMPLETION : owns
  APP_USER ||--|| USER_PREFERENCE : configures

  APP_USER { uuid id PK string email string password_hash string timezone }
  LIFE_CATEGORY { uuid id PK uuid user_id FK string name string color boolean archived }
  LIFE_ITEM { uuid id PK uuid user_id FK uuid category_id FK string type string status date scheduled_date string minimum_action }
  HABIT_COMPLETION { uuid id PK uuid habit_id FK date completion_date string state }
  WELLBEING_CHECKIN { uuid id PK int mood string energy int stress timestamptz recorded_at }
  WEEKLY_PLAN { uuid id PK date week_start string_array priorities text reflection }
  TASK_POSTPONEMENT { uuid id PK uuid task_id FK date previous_date date new_date string reason }
  FOCUS_SESSION { uuid id PK uuid task_id FK int planned_minutes int actual_seconds string status }
  GOAL_MILESTONE { uuid id PK uuid goal_id FK string title boolean completed }
  GOAL_PROGRESS { uuid id PK uuid goal_id FK decimal value string source }
  USER_PREFERENCE { uuid user_id PK boolean low_energy_mode boolean show_streaks }
```

User IDs are present on every sensitive aggregate to enforce ownership in every repository query. UTC `timestamptz` values are stored for instants; local dates remain date-only.
