CREATE TABLE authverificationtoken (
    authverification_ID              BIGINT AUTO_INCREMENT PRIMARY KEY,
    internal_userID        BIGINT NOT NULL,
    -- OTP specific fields
    otp_code        VARCHAR(10),
    otp_expires_at  DATETIME NOT NULL,
    otp_used        BOOLEAN NOT NULL DEFAULT FALSE,
    otp_attempts    INT NOT NULL DEFAULT 0,
    
    -- Email specific fields
    email_token     VARCHAR(255) NOT NULL,
    email_expires_at DATETIME NOT NULL,
    email_used      BOOLEAN NOT NULL DEFAULT FALSE,
    
    -- Shared state
    is_verified     BOOLEAN AS (otp_used AND email_used) VIRTUAL, -- Automatically true when both are done
    locked_until    DATETIME NULL,
    created_at      DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (internal_userID) REFERENCES users(internal_userID)
    
 );   