CREATE TABLE ChatReport (
    chat_report_ID INT PRIMARY KEY AUTO_INCREMENT,
    chat_ID INT NOT NULL,
    reporter_ID BIGINT NOT NULL,
    report_reason TEXT NOT NULL,
    admin_notes TEXT,
    status ENUM('Pending', 'Resolved') NOT NULL DEFAULT 'Pending',

    FOREIGN KEY (chat_ID) REFERENCES Chat(chat_ID)
	ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (reporter_ID) REFERENCES Users(internal_userID)
	ON DELETE CASCADE ON UPDATE CASCADE
);