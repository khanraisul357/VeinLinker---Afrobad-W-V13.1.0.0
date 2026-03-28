CREATE TABLE BloodRequest (
    request_ID INT PRIMARY KEY AUTO_INCREMENT,
    receiver_ID BIGINT NOT NULL,
    blood_group ENUM('A+', 'A-', 'B+', 'B-', 'AB+', 'AB-', 'O+', 'O-') NOT NULL,
    quantity INT NOT NULL,
    location_range VARCHAR(255),
    urgency_level ENUM('Emergency', 'Standard') NOT NULL,
    status ENUM('Open', 'Assigned', 'Fulfilled', 'Cancelled') NOT NULL DEFAULT 'Open',
    patient_name VARCHAR(255) NOT NULL,
    hospital_name VARCHAR(255) NOT NULL,
    room VARCHAR(255),
    floor VARCHAR(255),
    building VARCHAR(255),
    hospital_destination POINT NOT NULL SRID 4326,
    receiver_preferred_hours INT,
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key Constraint
    FOREIGN KEY (receiver_ID) REFERENCES Users(internal_userID)
	ON DELETE CASCADE
	ON UPDATE CASCADE,
    -- Spatial Index for location queries
    SPATIAL INDEX idx_hospital_destination (hospital_destination)
);