CREATE TABLE authverificationtoken (
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id        BIGINT NOT NULL,
    token          VARCHAR(255) NOT NULL,
    token_type     ENUM('OTP_PHONE', 'EMAIL_LINK') NOT NULL,
    expires_at     DATETIME NOT NULL,
    used           BOOLEAN NOT NULL DEFAULT FALSE,
    attempt_count  INT NOT NULL DEFAULT 0,
    locked_until   DATETIME NULL,
    resend_count   INT NOT NULL DEFAULT 0,
    created_at     DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_avt_user FOREIGN KEY (user_id) REFERENCES users(internal_userID)
);