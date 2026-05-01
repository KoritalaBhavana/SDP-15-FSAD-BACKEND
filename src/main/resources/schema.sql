CREATE DATABASE IF NOT EXISTS travelnextpro;
USE travelnextpro;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  role ENUM('TOURIST','HOST','GUIDE','ADMIN','CHEF') NOT NULL,
  phone VARCHAR(20),
  profile_image VARCHAR(500),
  bio TEXT,
  location VARCHAR(200),
  specialization VARCHAR(200),
  experience_years INT,
  languages VARCHAR(200),
  price_per_day DECIMAL(10,2),
  status ENUM('PENDING','APPROVED','REJECTED') DEFAULT 'PENDING',
  is_verified BOOLEAN DEFAULT FALSE,
  is_approved BOOLEAN DEFAULT FALSE,
  onboarding_completed BOOLEAN DEFAULT FALSE,
  auth_provider VARCHAR(20) DEFAULT 'LOCAL',
  is_new BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS homestays (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  host_id BIGINT NOT NULL,
  title VARCHAR(200),
  description TEXT,
  location VARCHAR(255),
  city VARCHAR(100),
  state VARCHAR(100),
  category VARCHAR(100),
  price_per_night DECIMAL(10,2),
  max_guests INT DEFAULT 1,
  amenities TEXT,
  image_url VARCHAR(500),
  rating DECIMAL(3,2) DEFAULT 0,
  review_count INT DEFAULT 0,
  distance_info VARCHAR(200),
  image_urls TEXT,
  is_available BOOLEAN DEFAULT TRUE,
  is_approved BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (host_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  homestay_id BIGINT NOT NULL,
  tourist_id BIGINT NOT NULL,
  check_in DATE NOT NULL,
  check_out DATE NOT NULL,
  guests INT DEFAULT 1,
  total_amount DECIMAL(10,2),
  status ENUM('PENDING','CONFIRMED','CANCELLED','COMPLETED') DEFAULT 'PENDING',
  special_requests TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (homestay_id) REFERENCES homestays(id),
  FOREIGN KEY (tourist_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS attractions (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(200),
  description TEXT,
  location VARCHAR(255),
  city VARCHAR(100),
  category VARCHAR(100),
  image_url VARCHAR(500),
  entry_fee DECIMAL(10,2),
  best_time VARCHAR(100),
  distance_km DECIMAL(5,2),
  rating DECIMAL(3,2),
  added_by BIGINT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (added_by) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS reviews (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  target_id BIGINT NOT NULL,
  target_type VARCHAR(20) NOT NULL,
  rating INT,
  comment TEXT,
  reply TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS itineraries (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  guide_id BIGINT NOT NULL,
  title VARCHAR(200),
  description TEXT,
  duration_days INT,
  places TEXT,
  price DECIMAL(10,2),
  image_urls TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (guide_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS messages (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  sender_id BIGINT NOT NULL,
  receiver_id BIGINT NOT NULL,
  message TEXT NOT NULL,
  is_read BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (sender_id) REFERENCES users(id),
  FOREIGN KEY (receiver_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS notifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  message TEXT NOT NULL,
  is_read BOOLEAN DEFAULT FALSE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS wishlists (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tourist_id BIGINT NOT NULL,
  homestay_id BIGINT NOT NULL,
  FOREIGN KEY (tourist_id) REFERENCES users(id),
  FOREIGN KEY (homestay_id) REFERENCES homestays(id)
);

CREATE TABLE IF NOT EXISTS dining_bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tourist_id BIGINT NOT NULL,
  chef_id BIGINT,
  restaurant_name VARCHAR(200),
  booking_date DATE NOT NULL,
  booking_time VARCHAR(20),
  guests INT DEFAULT 1,
  table_number INT,
  type VARCHAR(10) DEFAULT 'TABLE',
  status VARCHAR(20) DEFAULT 'PENDING',
  special_requests TEXT,
  total_amount DECIMAL(10,2),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tourist_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS guide_bookings (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tourist_id BIGINT NOT NULL,
  guide_id BIGINT NOT NULL,
  booking_date DATE NOT NULL,
  duration_days INT DEFAULT 1,
  activity_type VARCHAR(200),
  total_amount DECIMAL(10,2),
  status VARCHAR(20) DEFAULT 'PENDING',
  special_requests TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (tourist_id) REFERENCES users(id),
  FOREIGN KEY (guide_id) REFERENCES users(id)
);
