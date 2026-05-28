-- 1. Create the 'files' table first (since 'userfiles' depends on it)
CREATE TABLE files (
    file_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    fileName VARCHAR(255) NOT NULL,
    originalFileName VARCHAR(255) NOT NULL,
    fileUrl VARCHAR(255) NOT NULL,
    fileType VARCHAR(50) NOT NULL, -- e.g., 'IMAGE', 'PDF'
    mimeType VARCHAR(100),         -- e.g., 'image/jpeg', 'application/pdf'
    size BIGINT,
    uploadedAt DATETIME(6) NOT NULL,
    uploaded_by BIGINT NOT NULL,
    

    FOREIGN KEY (uploaded_by) REFERENCES users(internal_userID) 
    ON DELETE CASCADE ON UPDATE CASCADE
);
