CREATE TABLE authrefreshtoken (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id      BIGINT NOT NULL,
    token_hash   VARCHAR(255) NOT NULL UNIQUE,
    device_info  VARCHAR(255),
    ip_address   VARCHAR(45),
    expires_at   DATETIME NOT NULL,
    revoked      BOOLEAN NOT NULL DEFAULT FALSE,
    created_at   DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_art_user FOREIGN KEY (user_id) REFERENCES users(id)
);