CREATE TABLE accounts (
    id UUID PRIMARY KEY,
    balance NUMERIC(19,2) NOT NULL CHECK (balance >= 0)
);
