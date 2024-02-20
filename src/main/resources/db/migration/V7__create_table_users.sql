CREATE TABLE IF NOT EXISTS users (
  id bigserial PRIMARY KEY,
  user_name varchar(255) UNIQUE,
  full_name varchar(255),
  password varchar(255),
  account_non_expired boolean,
  account_non_locked boolean,
  credentials_non_expired boolean,
  enabled boolean
);
