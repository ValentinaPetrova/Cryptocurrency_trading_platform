CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 10000.00
);

CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    type VARCHAR(10),
    symbol VARCHAR(10),
    quantity DECIMAL(20, 8),
    price DECIMAL(20, 8),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);*/
