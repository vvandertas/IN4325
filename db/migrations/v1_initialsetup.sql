CREATE EXTENSION IF NOT EXISTS hstore;

CREATE TABLE sessions (
  id          VARCHAR(255) PRIMARY KEY,
  created_at  TIMESTAMP NOT NULL DEFAULT now(),
  accessed_at TIMESTAMP NOT NULL DEFAULT now(),
  saved_at    TIMESTAMP NOT NULL DEFAULT now(),
  expiry_at   TIMESTAMP NOT NULL DEFAULT now() + INTERVAL '30 days',
  attributes  HSTORE    NOT NULL DEFAULT ''
);

CREATE TABLE users (
  id               UUID PRIMARY KEY,
  participant_type VARCHAR(100) NOT NULL,
  created_at       TIMESTAMP    NOT NULL DEFAULT now(),
  current_task_id INT NOT NULL DEFAULT 1
);


CREATE TABLE tasks (
  id       SERIAL PRIMARY KEY,
  question VARCHAR NOT NULL,
  name      VARCHAR(50)
);

CREATE TABLE hints (
  id      SERIAL PRIMARY KEY,
  task_id INT     NOT NULL REFERENCES tasks (id),
  hint    VARCHAR NOT NULL
);

CREATE TABLE user_task_data (
  user_id      UUID      NOT NULL REFERENCES users (id),
  task_id      INT       NOT NULL REFERENCES tasks (id),
  created_at   TIMESTAMP NOT NULL DEFAULT now(),
  query_count  INT       NOT NULL DEFAULT 0,
  attempts     INT       NOT NULL DEFAULT 0,

  answer_found BOOLEAN   NOT NULL DEFAULT FALSE,
  finished_at  TIMESTAMP
);

CREATE TABLE visited_urls (
  id      SERIAL PRIMARY KEY,
  user_id UUID NOT NULL REFERENCES users (id),
  task_id INT  NOT NULL REFERENCES tasks (id),

  url     TEXT NOT NULL
);


CREATE TABLE keywords (
  id      SERIAL PRIMARY KEY,
  task_id INT  NOT NULL REFERENCES tasks (id),
  keyword TEXT NOT NULL
);