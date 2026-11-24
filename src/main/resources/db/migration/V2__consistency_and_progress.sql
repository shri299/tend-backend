alter table life_item add column habit_type varchar(20);
alter table life_item add column unit varchar(30);
alter table life_item add column ideal_target numeric(12,2);
alter table life_item add column minimum_target numeric(12,2);
alter table life_item add column selected_weekdays smallint[];
alter table life_item add column target_per_week smallint;
alter table life_item add column progress_model varchar(20);
alter table life_item add column target_value numeric(12,2);
alter table life_item add column progress_value numeric(12,2);
alter table life_category add column sort_order int not null default 0;

alter table habit_completion add column actual_value numeric(12,2);
alter table habit_completion add column updated_at timestamptz not null default now();
alter table habit_completion add constraint ck_habit_state check (state in ('COMPLETED','PARTIAL','SKIPPED','MISSED'));
create index idx_habit_completion_user_date on habit_completion(user_id, completion_date);

create table task_postponement (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  task_id uuid not null references life_item(id) on delete cascade,
  previous_date date, new_date date not null, reason varchar(40), initiation_stage varchar(20),
  created_at timestamptz not null default now()
);
create index idx_postponement_task on task_postponement(user_id, task_id, created_at desc);

create table focus_session (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  task_id uuid not null references life_item(id) on delete cascade,
  planned_minutes int not null check (planned_minutes between 1 and 240),
  actual_seconds int not null default 0 check (actual_seconds >= 0),
  status varchar(20) not null check (status in ('ACTIVE','PAUSED','FINISHED')),
  outcome varchar(30), reflection text, started_at timestamptz not null default now(),
  paused_at timestamptz, finished_at timestamptz, updated_at timestamptz not null default now()
);
create unique index uq_active_focus_per_user on focus_session(user_id) where status in ('ACTIVE','PAUSED');
create index idx_focus_user_started on focus_session(user_id, started_at desc);

create table goal_milestone (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  goal_id uuid not null references life_item(id) on delete cascade, title varchar(200) not null,
  completed boolean not null default false, sort_order int not null default 0,
  completed_at timestamptz, created_at timestamptz not null default now()
);
create index idx_milestone_goal on goal_milestone(user_id, goal_id, sort_order);

create table goal_progress (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  goal_id uuid not null references life_item(id) on delete cascade,
  value numeric(12,2), note text, source varchar(20) not null default 'MANUAL',
  recorded_at timestamptz not null default now()
);
create index idx_goal_progress on goal_progress(user_id, goal_id, recorded_at desc);

create table user_preference (
  user_id uuid primary key references app_user(id) on delete cascade,
  low_energy_mode boolean not null default false,
  show_streaks boolean not null default false,
  updated_at timestamptz not null default now()
);
