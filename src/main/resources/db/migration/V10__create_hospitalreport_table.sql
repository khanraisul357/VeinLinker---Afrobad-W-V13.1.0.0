CREATE TABLE HospitalReport (
    report_id INT PRIMARY KEY AUTO_INCREMENT,
    hospital_id INT NOT NULL,
    donor_id BIGINT,
    reason ENUM('NoShow', 'Other') NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    notes TEXT,

    FOREIGN KEY (hospital_id) REFERENCES Hospital(hospital_id)
	ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (donor_id) REFERENCES Users(internal_userID)
	ON DELETE SET NULL ON UPDATE CASCADE
);