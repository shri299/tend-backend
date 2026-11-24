create extension if not exists pgcrypto;

create table app_user (
  id uuid primary key default gen_random_uuid(),
  email varchar(254) not null unique,
  password_hash varchar(100) not null,
  display_name varchar(80) not null,
  timezone varchar(50) not null default 'UTC',
  created_at timestamptz not null default now()
);

create table life_category (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  name varchar(80) not null, color varchar(7) not null, icon varchar(40) not null default 'sparkles',
  archived boolean not null default false, created_at timestamptz not null default now(),
  unique(user_id, name)
);

create table life_item (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  category_id uuid references life_category(id) on delete set null,
  type varchar(20) not null check (type in ('BRAIN_DUMP','GOAL','TASK','HABIT')),
  title varchar(200) not null, description text, status varchar(30) not null default 'ACTIVE',
  priority varchar(20), energy varchar(20), due_date date, scheduled_date date,
  minimum_action varchar(300), recurrence varchar(100), parent_id uuid references life_item(id) on delete cascade,
  sort_order int not null default 0, processed boolean not null default false,
  completed_at timestamptz, created_at timestamptz not null default now(), updated_at timestamptz not null default now()
);
create index idx_life_item_user_type on life_item(user_id, type);
create index idx_life_item_user_schedule on life_item(user_id, scheduled_date);

create table habit_completion (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  habit_id uuid not null references life_item(id) on delete cascade, completion_date date not null,
  state varchar(30) not null, note text, created_at timestamptz not null default now(),
  unique(user_id, habit_id, completion_date)
);

create table wellbeing_checkin (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  mood smallint check (mood between 1 and 5), energy varchar(20) not null,
  stress smallint check (stress between 1 and 5), note text, recorded_at timestamptz not null default now()
);
create index idx_checkin_user_recorded on wellbeing_checkin(user_id, recorded_at desc);

create table weekly_plan (
  id uuid primary key default gen_random_uuid(), user_id uuid not null references app_user(id) on delete cascade,
  week_start date not null, priorities text[] not null default '{}', notes text,
  reflection text, created_at timestamptz not null default now(), updated_at timestamptz not null default now(),
  unique(user_id, week_start)
);
