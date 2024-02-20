CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    author TEXT,
    launch_date TIMESTAMP NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    title TEXT NOT NULL
);
