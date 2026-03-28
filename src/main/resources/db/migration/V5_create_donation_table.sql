CREATE TABLE Donation (
    donation_ID INT PRIMARY KEY AUTO_INCREMENT,
    donor_ID BIGINT,
    request_ID INT UNIQUE,
    date DATE NOT NULL,
    verified_quantity INT,
    next_eligible_date DATE,

    FOREIGN KEY (donor_ID) REFERENCES Users(internal_userID)
	ON DELETE SET NULL ON UPDATE CASCADE,

    FOREIGN KEY (request_ID) REFERENCES BloodRequest(request_ID)
	ON DELETE SET NULL ON UPDATE CASCADE
);