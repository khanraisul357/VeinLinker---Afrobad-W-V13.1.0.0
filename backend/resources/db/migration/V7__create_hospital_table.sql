CREATE TABLE Hospital (
    hospital_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    location POINT SRID 4326 NOT NULL,
    address TEXT,
    verified_status BOOLEAN NOT NULL DEFAULT FALSE,

    SPATIAL INDEX idx_hospital_location (location)
);