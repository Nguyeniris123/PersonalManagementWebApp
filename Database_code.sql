USE persondb;
-- Bảng tài khoản người dùng / chuyên gia
CREATE TABLE user_account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    role ENUM('ROLE_USER', 'ROLE_TRAINER', 'ROLE_ADMIN') DEFAULT 'ROLE_USER',
    full_name VARCHAR(100),
    gender ENUM('MALE', 'FEMALE', 'OTHER'),
    date_of_birth DATE,
    phone VARCHAR(20),
    avatar VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE health_profile (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL UNIQUE,  -- Ràng buộc UNIQUE để đảm bảo mỗi user chỉ có 1 hồ sơ
    height FLOAT,
    weight FLOAT,
    bmi FLOAT,
    heart_rate INT,
    target TEXT,
    steps_per_day INT,
    water_intake FLOAT,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
) ENGINE=InnoDB;


-- Bảng kết nối giữa user và huấn luyện viên / chuyên gia
CREATE TABLE user_trainer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    trainer_id INT NOT NULL,
    status ENUM('PENDING', 'ACCEPTED', 'REJECTED') DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE,
    FOREIGN KEY (trainer_id) REFERENCES user_account(id) ON DELETE CASCADE
);

-- Bảng kế hoạch tập luyện
CREATE TABLE workout_plan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_account(id)
);

-- Bảng danh sách bài tập
CREATE TABLE exercise (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    muscle_group VARCHAR(50), -- ví dụ: "Chest", "Legs"
    level VARCHAR(20),        -- "Beginner", "Intermediate", "Advanced"
    calories_burned INT
);

CREATE TABLE workout_plan_exercise (
    id INT PRIMARY KEY AUTO_INCREMENT,
    workout_plan_id INT NOT NULL,
    exercise_id INT NOT NULL,
    sets INT,
    reps INT,
    duration_minutes INT,
    FOREIGN KEY (workout_plan_id) REFERENCES workout_plan(id),
    FOREIGN KEY (exercise_id) REFERENCES exercise(id)
);

-- Bảng thực đơn dinh dưỡng
CREATE TABLE meal_plan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    meal_type ENUM('BREAKFAST', 'LUNCH', 'DINNER', 'SNACK'),
    description TEXT,
    calories INT,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);

-- Bảng nhật ký sức khỏe
CREATE TABLE health_journal (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    date DATE NOT NULL,
    content TEXT,
    feeling TEXT,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);

-- Bảng nhắc nhở (uống nước, tập luyện, nghỉ ngơi)
CREATE TABLE reminder (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100),
    reminder_type ENUM('DRINK_WATER', 'WORKOUT', 'REST'),
    time TIME,
    is_active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (user_id) REFERENCES user_account(id) ON DELETE CASCADE
);

-- Bảng chat giữa user và trainer (nâng cao)
CREATE TABLE chat_message (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    message TEXT NOT NULL,
    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (sender_id) REFERENCES user_account(id) ON DELETE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user_account(id) ON DELETE CASCADE
);