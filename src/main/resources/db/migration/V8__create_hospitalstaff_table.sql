CREATE TABLE HospitalStaff (
    hospital_staff_ID INT PRIMARY KEY AUTO_INCREMENT,
    hospital_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(255),

    FOREIGN KEY (hospital_id) REFERENCES Hospital(hospital_id)
	ON DELETE CASCADE ON UPDATE CASCADE
);