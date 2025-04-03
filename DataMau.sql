USE persondb;
INSERT INTO user_account (username, password, email, role, full_name, gender, date_of_birth, phone, avatar)
VALUES
('john_doe', '1', 'john.doe@example.com', 'USER', 'John Doe', 'MALE', '1990-01-15', '0123456789', 'avatar1.jpg'),
('jane_smith', '1', 'jane.smith@example.com', 'TRAINER', 'Jane Smith', 'FEMALE', '1985-07-22', '0987654321', 'avatar2.jpg'),
('admin_user', '1', 'admin@example.com', 'ADMIN', 'Admin User', 'MALE', '1980-05-05', '0112233445', 'admin_avatar.jpg');

USE persondb;
INSERT INTO health_profile (user_id, height, weight, bmi, heart_rate, target, steps_per_day, water_intake)
VALUES
(1, 1.75, 70, 22.9, 75, 'Maintain current health', 10000, 2.5),
(2, 1.68, 60, 21.3, 80, 'Lose weight and increase fitness', 12000, 3),
(3, 1.80, 85, 26.2, 70, 'Gain muscle mass', 8000, 3.5);