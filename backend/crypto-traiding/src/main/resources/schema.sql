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
/*-- USERS
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    balance DECIMAL(15, 2) NOT NULL DEFAULT 10000.00
);

-- TRANSACTIONS
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id),
    type VARCHAR(10), -- 'buy' or 'sell'
    symbol VARCHAR(10),
    quantity DECIMAL(20, 8),
    price DECIMAL(20, 8),
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
/*-- Users table
CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Accounts table
CREATE TABLE accounts (
    account_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(user_id),
    balance DECIMAL(20, 8) NOT NULL DEFAULT 10000.00,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Cryptocurrencies table (static reference data)
CREATE TABLE cryptocurrencies (
    crypto_id SERIAL PRIMARY KEY,
    kraken_symbol VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    symbol VARCHAR(10) NOT NULL
);

-- Transactions table
CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(account_id),
    crypto_id INTEGER REFERENCES cryptocurrencies(crypto_id),
    type VARCHAR(4) CHECK (type IN ('BUY', 'SELL')),
    quantity DECIMAL(20, 8) NOT NULL,
    price DECIMAL(20, 8) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Holdings table
CREATE TABLE holdings (
    holding_id SERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES accounts(account_id),
    crypto_id INTEGER REFERENCES cryptocurrencies(crypto_id),
    quantity DECIMAL(20, 8) NOT NULL DEFAULT 0,
    avg_purchase_price DECIMAL(20, 8),
    UNIQUE(account_id, crypto_id)
);*/
