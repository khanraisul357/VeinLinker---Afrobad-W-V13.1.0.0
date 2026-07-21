CREATE TABLE BloodBank (
    blood_bank_ID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(255) UNIQUE,
    bank_type ENUM('hospital', 'external') NOT NULL DEFAULT 'external',
    location POINT SRID 4326 NOT NULL ,  -- Fixed: Added NOT NULL + default
    phone VARCHAR(20),
    email VARCHAR(255) UNIQUE,
    emergency_phone VARCHAR(20),
    contact_person VARCHAR(255),
    operating_hours JSON,
    capacity_ml INT DEFAULT 100000,
    license_number VARCHAR(100) UNIQUE,
    license_expiry DATE,
    status ENUM('Active', 'Inactive', 'Suspended') NOT NULL DEFAULT 'Active',
    is_24hr BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    SPATIAL INDEX idx_bloodbank_location (location)
);