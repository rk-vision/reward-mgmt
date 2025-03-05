DROP TABLE IF EXISTS transaction;

CREATE TABLE transaction (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date TIMESTAMP NOT NULL
); 