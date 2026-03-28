CREATE TABLE DonationVerification (
    verification_ID INT PRIMARY KEY AUTO_INCREMENT,
    acceptance_ID INT UNIQUE NOT NULL,
    blood_bank_ID INT,
    donor_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    receiver_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    hospital_verified BOOLEAN NOT NULL DEFAULT FALSE,
    hospital_staff_ID INT,
    hospital_timestamp TIMESTAMP NULL DEFAULT NULL,
    scan_timestamp TIMESTAMP NULL DEFAULT NULL,
    gps_coordinates POINT SRID 4326 NOT NULL,
    hospital_slip_image VARCHAR(512),
    fallback_confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    official_quantity INT,
    recovery_period_days INT NOT NULL DEFAULT 90,

    FOREIGN KEY (acceptance_ID) REFERENCES RequestAcceptance(acceptance_ID)
	ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (blood_bank_ID) REFERENCES BloodBank(blood_bank_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,

    FOREIGN KEY (hospital_staff_ID) REFERENCES HospitalStaff(hospital_staff_ID)
	ON DELETE SET NULL ON UPDATE CASCADE,

    SPATIAL INDEX idx_donationverification_gps (gps_coordinates)
);