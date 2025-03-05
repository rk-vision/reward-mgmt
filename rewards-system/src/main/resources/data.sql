-- Customer 1: Mix of transactions across different reward tiers
INSERT INTO transaction (customer_id, amount, transaction_date) VALUES 
(1, 120.00, DATEADD('DAY', -5, CURRENT_TIMESTAMP())),    -- 90 points (2*20 + 1*50)
(1, 85.00, DATEADD('DAY', -10, CURRENT_TIMESTAMP())),    -- 35 points
(1, 160.00, DATEADD('MONTH', -1, CURRENT_TIMESTAMP())),  -- 170 points (2*60 + 1*50)
(1, 95.00, DATEADD('MONTH', -1, CURRENT_TIMESTAMP())),   -- 45 points
(1, 75.00, DATEADD('MONTH', -2, CURRENT_TIMESTAMP())),   -- 25 points
(1, 200.00, DATEADD('MONTH', -2, CURRENT_TIMESTAMP()));  -- 250 points (2*100 + 1*50)

-- Customer 2: Mostly high-value transactions
INSERT INTO transaction (customer_id, amount, transaction_date) VALUES 
(2, 130.00, DATEADD('DAY', -15, CURRENT_TIMESTAMP())),   -- 110 points
(2, 150.00, DATEADD('DAY', -20, CURRENT_TIMESTAMP())),   -- 150 points
(2, 200.00, DATEADD('MONTH', -1, CURRENT_TIMESTAMP())),  -- 250 points
(2, 110.00, DATEADD('MONTH', -2, CURRENT_TIMESTAMP()));  -- 70 points

-- Customer 3: Mostly low-value transactions
INSERT INTO transaction (customer_id, amount, transaction_date) VALUES 
(3, 45.00, DATEADD('DAY', -2, CURRENT_TIMESTAMP())),     -- 0 points
(3, 55.00, DATEADD('DAY', -12, CURRENT_TIMESTAMP())),    -- 5 points
(3, 65.00, DATEADD('MONTH', -1, CURRENT_TIMESTAMP())),   -- 15 points
(3, 80.00, DATEADD('MONTH', -2, CURRENT_TIMESTAMP()));   -- 30 points

-- Customer 4: Mix of transactions with some edge cases
INSERT INTO transaction (customer_id, amount, transaction_date) VALUES 
(4, 100.00, DATEADD('DAY', -1, CURRENT_TIMESTAMP())),    -- 50 points
(4, 101.00, DATEADD('DAY', -8, CURRENT_TIMESTAMP())),    -- 52 points
(4, 50.00, DATEADD('MONTH', -1, CURRENT_TIMESTAMP())),   -- 0 points
(4, 50.01, DATEADD('MONTH', -2, CURRENT_TIMESTAMP()));   -- 0.01 points 