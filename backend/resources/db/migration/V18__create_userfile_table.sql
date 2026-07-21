-- 2. Create the 'userfiles' junction/mapping table
CREATE TABLE userfiles (
    userfile_ID BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_ID BIGINT NOT NULL,
    file_ID BIGINT NOT NULL,
    document_type ENUM('PROFILE_IMAGE', 'NID_FRONT', 'NID_BACK', 'PASSPORT') NOT NULL,
    status ENUM('UPLOADED', 'VERIFIED', 'REJECTED') NOT NULL DEFAULT 'UPLOADED',
    
    -- Foreign Key 
    FOREIGN KEY (user_ID) REFERENCES users(internal_userID) 
    ON DELETE CASCADE ON UPDATE CASCADE,
        
    -- Foreign Key 
    FOREIGN KEY (file_ID) REFERENCES files(file_ID) 
    ON DELETE CASCADE ON UPDATE CASCADE
);