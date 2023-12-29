CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DOUBLE NOT NULL,
    currency CHAR(3) NOT NULL,
    `type` VARCHAR(6) NOT NULL,
    customer_name VARCHAR(255) NOT NULL,
    transaction_date DATE NOT NULL,
    notes VARCHAR(100)
);