INSERT INTO USERS (user_name, email, skill_level, password, roles) VALUES
('player1', 'p1@example.com', 'Beginner', '$2a$10$9vL9z7K8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8.', 'USER'),
('organizer1', 'org1@example.com', 'Advanced', '$2a$10$9vL9z7K8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8Z8.', 'ORGANIZER');

INSERT INTO MATCH (date, location, max_player, status, organizer_id, latitude, longitude) VALUES
('2025-03-15 14:00:00', 'London Park', 10, 'OPEN', 2, 51.5074, -0.1278);