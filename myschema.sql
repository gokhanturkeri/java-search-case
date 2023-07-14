CREATE TABLE IF NOT EXISTS city (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
    id INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS property (
    id INT PRIMARY KEY,
    category_id INT,
    title VARCHAR(255) NOT NULL,
    city_id INT,
    price DECIMAL(19, 4),
    currency VARCHAR(3),
    FOREIGN KEY (category_id) REFERENCES category(id),
    FOREIGN KEY (city_id) REFERENCES city(id)
);

INSERT IGNORE INTO city (id, name) VALUES (1, 'Istanbul');
INSERT IGNORE INTO city (id, name) VALUES (2, 'Ankara');
INSERT IGNORE INTO city (id, name) VALUES (3, 'Izmir');
INSERT IGNORE INTO city (id, name) VALUES (4, 'Bursa');
INSERT IGNORE INTO city (id, name) VALUES (5, 'Antalya');

INSERT IGNORE INTO category (id, name) VALUES (1, 'konut');
INSERT IGNORE INTO category (id, name) VALUES (2, 'ticari');
INSERT IGNORE INTO category (id, name) VALUES (3, 'arsa');
