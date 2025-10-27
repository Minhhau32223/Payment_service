-- Create database
CREATE DATABASE IF NOT EXISTS `payment_service`;
USE `payment_service`;

-- Payments table
CREATE TABLE payments (
    id BINARY(16) NOT NULL PRIMARY KEY,               -- UUID (PaymentID)
    order_id BINARY(16) NOT NULL,                     -- UUID (OrderID)
    customer_id BINARY(16) NOT NULL,                  -- UUID (CustomerID)
    amount DECIMAL(10,2) NOT NULL,                    -- Số tiền
    payment_status VARCHAR(20) NOT NULL,              -- PENDING / COMPLETED / FAILED / CANCELLED
    payment_method VARCHAR(50) NOT NULL,              -- CREDIT_CARD / DEBIT_CARD / BANK_TRANSFER / E_WALLET / CASH_ON_DELIVERY
    failure_message TEXT,                             -- Lý do lỗi (nếu có)
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    INDEX idx_order_id (order_id),
    INDEX idx_customer_id (customer_id),
    INDEX idx_status (payment_status)
);
CREATE TABLE payment_events (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    payment_id BINARY(16) NOT NULL,                   -- Khóa ngoại đến payments.id
    event_type VARCHAR(50) NOT NULL,                  -- e.g. PAYMENT_INITIATED, PAYMENT_COMPLETED
    event_payload JSON NOT NULL,                      -- Nội dung chi tiết event (orderId, amount, ...)
    published BOOLEAN DEFAULT FALSE,                  -- Đánh dấu đã publish sang Kafka hay chưa
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE
);

INSERT INTO payments (
    id, order_id, customer_id, amount, payment_status, payment_method, failure_message, created_at, updated_at
) VALUES
(UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), UUID_TO_BIN('aaaa1111-1111-1111-1111-aaaaaaaaaaaa'), UUID_TO_BIN('bbbb1111-1111-1111-1111-bbbbbbbbbbbb'),
 150000.00, 'COMPLETED', 'CREDIT_CARD', NULL, NOW(), NOW()),

(UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), UUID_TO_BIN('aaaa2222-2222-2222-2222-aaaaaaaaaaaa'), UUID_TO_BIN('bbbb2222-2222-2222-2222-bbbbbbbbbbbb'),
 200000.00, 'FAILED', 'BANK_TRANSFER', 'Insufficient funds', NOW(), NOW()),

(UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), UUID_TO_BIN('aaaa3333-3333-3333-3333-aaaaaaaaaaaa'), UUID_TO_BIN('bbbb3333-3333-3333-3333-bbbbbbbbbbbb'),
 180000.00, 'PENDING', 'E_WALLET', NULL, NOW(), NOW());

-- Indexes for faster queries
CREATE INDEX idx_payments_order_id ON payments(order_id);
CREATE INDEX idx_payments_customer_id ON payments(customer_id);
CREATE INDEX idx_payments_status ON payments(payment_status);
CREATE INDEX idx_payments_created_at ON payments(created_at);
CREATE INDEX idx_payments_order_status ON payments(order_id, payment_status);

-- Comments
ALTER TABLE payments COMMENT = 'Stores payment transaction records';

