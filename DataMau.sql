USE persondb;
INSERT INTO user_account (username, password, email, role, full_name, gender, date_of_birth, phone, avatar)
VALUES
('admin_user', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'admin@example.com', 'ROLE_ADMIN', 'ROLEAdmin User', 'MALE', '1980-05-05', '0112233445', 'https://res.cloudinary.com/dnwyvuqej/image/upload/v1733497572/tqazghsjhudjzdz4rhrh.jpg'),
('john_doe', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'john.doe@example.com', 'ROLE_USER', 'John Doe', 'MALE', '1990-01-15', '0123456789', 'https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg'),
('jane_smith', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'jane.smith@example.com', 'ROLE_TRAINER', 'Jane Smith', 'FEMALE', '1985-07-22', '0987654321', 'https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg'),
('nguyen', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'nguyen.doe@example.com', 'ROLE_USER', 'John Doe', 'MALE', '1990-01-15', '0123456789', 'https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg'),
('abc', '$2a$10$5X9k5N1sTc1/CjVH5XJoje3QMYijH3ETpgkox00R0MdPaJPPrf7wO', 'abc.doe@example.com', 'ROLE_USER', 'John Doe', 'MALE', '1990-01-15', '0123456789', 'https://res.cloudinary.com/dnwyvuqej/image/upload/v1733499646/default_avatar_uv0h7z.jpg');

USE persondb;
INSERT INTO user_trainer (user_id, trainer_id, status)
VALUES 
(1, 2, 'PENDING'),
(12, 19, 'ACCEPTED');

USE persondb;
INSERT INTO health_profile (user_id, height, weight, bmi, heart_rate, target, steps_per_day, water_intake)
VALUES
(1, 170, 70, 22.9, 75, 'Maintain current health', 10000, 2.5),
(2, 180, 60, 21.3, 80, 'Lose weight and increase fitness', 12000, 3),
(3, 150, 85, 26.2, 70, 'Gain muscle mass', 8000, 3.5);

USE persondb;
INSERT INTO workout_plan (user_id, name, start_date, end_date)
VALUES 
(1, 'Kế hoạch tập tăng cơ 4 tuần', '2025-04-01', '2025-04-28'),
(1, 'Lịch tập cardio giảm cân', '2025-04-10', '2025-05-10'),
(2, 'Tập gym buổi sáng cho người bận rộn', '2025-04-05', '2025-05-05'),
(3, 'Kế hoạch duy trì sức khỏe hàng ngày', '2025-04-01', '2025-04-30');

USE persondb;
INSERT INTO exercise (name, description, muscle_group, level, calories_burned) VALUES
('Hít đất', 'Bài tập đơn giản giúp phát triển cơ ngực và tay sau.', 'Chest', 'Beginner', 100),
('Gập bụng', 'Tăng cường cơ bụng, dễ thực hiện tại nhà.', 'Abs', 'Beginner', 80),
('Squat', 'Phát triển cơ đùi, mông và cải thiện sức mạnh chân.', 'Legs', 'Intermediate', 150),
('Plank', 'Giúp siết cơ bụng và cải thiện sức bền.', 'Core', 'Beginner', 60),
('Deadlift', 'Tác động toàn thân, đặc biệt là lưng và chân.', 'Back', 'Advanced', 250);

USE persondb;
INSERT INTO workout_plan_exercise (workout_plan_id, exercise_id, sets, reps, duration_minutes) VALUES
(1, 1, 3, 12, 0),
(1, 2, 4, 10, 0),
(1, 3, 0, 0, 15),  -- bài tập theo phút, không tính reps/sets
(2, 2, 3, 8, 0),
(2, 4, 4, 10, 0),
(3, 1, 2, 20, 0),
(3, 5, 0, 0, 30);

USE persondb;
INSERT INTO statistics (user_id, duration_minutes, calories_burned, start_date, end_date)
VALUES 
(1, 30, 250, '2025-05-22 06:30:00', '2025-05-22 07:00:00'),
(2, 45, 400, '2025-05-22 08:00:00', '2025-05-22 08:45:00');

