

CREATE DATABASE IF NOT EXISTS article_feed;

USE article_feed;

CREATE TABLE IF NOT EXISTS feeds (
    feed_id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    feed_name VARCHAR(50) NOT NULL,
    feed_url VARCHAR(2048) NOT NULL
);