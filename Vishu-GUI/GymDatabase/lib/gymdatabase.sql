SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";
SET NAMES utf8mb4;
SET CHARACTER SET utf8mb4;

-- Drop tables if they already exist
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `workout_plans`;
DROP TABLE IF EXISTS `classes`;
DROP TABLE IF EXISTS `diet_plans`;
DROP TABLE IF EXISTS `members`;
DROP TABLE IF EXISTS `trainers`;

-- Create the `trainers` table
CREATE TABLE `trainers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `expertise` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create the `members` table
CREATE TABLE `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `membership_plan` varchar(255) DEFAULT 'platinum',
  `age` int(11) DEFAULT 18,
  `is_trainer` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create the `classes` table
CREATE TABLE `classes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(100) NOT NULL,
  `class_time` time NOT NULL,
  `trainer_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_trainer` FOREIGN KEY (`trainer_id`) REFERENCES `trainers` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create the `diet_plans` table
CREATE TABLE `diet_plans` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `food_item` varchar(100) NOT NULL,
  `description` text NOT NULL,
  `calorie_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create the `payments` table
CREATE TABLE `payments` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `amount` decimal(10,2) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Create the `workout_plans` table
CREATE TABLE `workout_plans` (
  `plan_id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL,
  `plan_description` text DEFAULT NULL,
  PRIMARY KEY (`plan_id`),
  CONSTRAINT `workout_plans_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Insert data into `trainers`
INSERT INTO `trainers` (`name`, `expertise`) VALUES
('Alice Brown', 'Weightlifting'),
('Bob Green', 'Nutrition'),
('Charlie White', 'Weightloss');

-- Insert data into `members`
INSERT INTO `members` (`name`, `password`, `membership_plan`, `age`, `is_trainer`) VALUES
('John Doe', 'password123', 'Gold', 25, 0),
('Jane Smith', 'mypassword', 'Silver', 34, 0),
('Mike Johnson', 'secret', 'Platinum', 17, 0),
('Nidhi', NULL, 'Gold', 19, 0),
('Vishu', NULL, 'Gold', 19, 0),
('Alice Brown', 'alice123', NULL, 35, 1),
('Bob Green', 'bob123', NULL, 40, 1),
('Charlie White', 'charlie123', NULL, 40, 1);

-- Insert data into `classes`
INSERT INTO `classes` (`class_name`, `class_time`, `trainer_id`) VALUES
('Deadlifting', '21:16:16', 1);

-- Insert data into `diet_plans`
INSERT INTO `diet_plans` (`food_item`, `description`, `calorie_count`) VALUES
('Breakfast', 'Oatmeal with fruits and nuts', 400),
('Lunch', 'Grilled chicken with steamed vegetables', 800),
('Dinner', 'Salmon with quinoa and broccoli', 300),
('Snacks', 'Greek yogurt and almonds', 150);

-- Insert data into `payments`
INSERT INTO `payments` (`member_id`, `amount`, `date`) VALUES
(1, 50.00, '2024-10-01'),
(2, 30.00, '2024-10-02'),
(3, 70.00, '2024-10-03'),
(1, 123.00, NULL),
(1, 120.00, NULL),
(1, 1233.00, NULL),
(4, 2000.00, NULL),
(5, 458.00, NULL);

-- Insert data into `workout_plans`
INSERT INTO `workout_plans` (`member_id`, `plan_description`) VALUES
(1, '3x a week strength training focusing on major muscle groups.'),
(2, '5x a week cardio training with HIIT sessions.'),
(3, 'Full-body workouts 4x a week with flexibility exercises.');

COMMIT;
