CREATE TABLE MedicalCertificate (
    certificate_id INT PRIMARY KEY AUTO_INCREMENT,
    donor_id BIGINT NOT NULL,
    hospital_id INT NOT NULL,
    request_id INT UNIQUE NOT NULL,
    issue_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    digital_signature VARCHAR(512),
    director_name VARCHAR(255),
    status ENUM('Issued', 'Claimed') NOT NULL DEFAULT 'Issued',

    FOREIGN KEY (donor_id) REFERENCES Users(internal_userID)
	ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (hospital_id) REFERENCES Hospital(hospital_id)
	ON DELETE RESTRICT ON UPDATE CASCADE,

    FOREIGN KEY (request_id) REFERENCES BloodRequest(request_ID)
	ON DELETE RESTRICT ON UPDATE CASCADE
);