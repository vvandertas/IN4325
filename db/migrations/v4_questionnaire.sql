CREATE EXTENSION IF NOT EXISTS hstore;
ALTER TABLE users ADD COLUMN questionnaire HSTORE;