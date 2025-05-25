INSERT INTO users (name)
SELECT 'user1'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE name = 'user1'
);
INSERT INTO users (name)
SELECT 'user2'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE name = 'user2'
);
INSERT INTO users (name)
SELECT 'user3'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE name = 'user3'
);
INSERT INTO users (name)
SELECT 'user4'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE name = 'user4'
);
INSERT INTO users (name)
SELECT 'user5'
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE name = 'user5'
);
