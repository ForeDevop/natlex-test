CREATE TABLE IF NOT EXISTS sections (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS geoclasses (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(255),
    code varchar(255),
    section_id int,
    PRIMARY KEY (id),
    FOREIGN KEY (section_id) REFERENCES sections(id));

CREATE TABLE IF NOT EXISTS files (
    id int NOT NULL AUTO_INCREMENT,
    status varchar(255),
    PRIMARY KEY (id));