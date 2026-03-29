CREATE TABLE role (
    roleID INT PRIMARY KEY AUTO_INCREMENT,
    internal_userID BIGINT NOT NULL,
    role_name ENUM('Donor', 'Receiver') NOT NULL,
    -- Foreign Key Constraint
	FOREIGN KEY (internal_userID) REFERENCES Users(internal_userID)
	ON DELETE CASCADE
	ON UPDATE CASCADE
);