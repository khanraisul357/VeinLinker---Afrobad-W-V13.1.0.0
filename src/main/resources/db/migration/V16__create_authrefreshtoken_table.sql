CREATE TABLE authrefreshtoken (
    authrefresh_ID           BIGINT AUTO_INCREMENT PRIMARY KEY,
    internal_userID    BIGINT NOT NULL,
    token_hash   VARCHAR(255) NOT NULL UNIQUE,
    device_info  VARCHAR(255),
    ip_address   VARCHAR(45),
    expires_at   DATETIME NOT NULL,
    revoked      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (internal_userID) REFERENCES users(internal_userID)
);