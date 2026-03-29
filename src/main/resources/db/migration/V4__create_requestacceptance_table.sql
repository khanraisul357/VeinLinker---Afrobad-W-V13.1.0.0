CREATE TABLE RequestAcceptance (
    acceptance_ID INT PRIMARY KEY AUTO_INCREMENT,
    request_ID INT NOT NULL,
    donor_ID BIGINT NOT NULL,
    blood_bank_ID INT,
    status ENUM(
        'Accepted',
        'Rejected',
        'Committed',
        'Pass Commit',
        'StatusCheckPassed',
        'StatusCheckFailed',
        'LivelinessCheckPassed',
        'LivelinessCheckFailed'
    ) NOT NULL,
    accept_timestamp TIMESTAMP NULL DEFAULT NULL,
    reject_timestamp TIMESTAMP NULL DEFAULT NULL,
    commit_deadline TIMESTAMP NULL DEFAULT NULL,
    commit_timestamp TIMESTAMP NULL DEFAULT NULL,
    pass_commit TIMESTAMP NULL DEFAULT NULL,
    queue_position INT,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    qr_code VARCHAR(512) UNIQUE,
    destination_hospital_id INT,
    in_transit_status BOOLEAN NOT NULL DEFAULT FALSE,

    -- Foreign Key Constraints
        FOREIGN KEY (request_ID)REFERENCES BloodRequest(request_ID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

        FOREIGN KEY (donor_ID)REFERENCES Users(internal_userID)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

        FOREIGN KEY (blood_bank_ID)REFERENCES BloodBank(blood_bank_ID)
        ON DELETE SET NULL
        ON UPDATE CASCADE,

        FOREIGN KEY (destination_hospital_id)REFERENCES BloodBank(blood_bank_ID)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);