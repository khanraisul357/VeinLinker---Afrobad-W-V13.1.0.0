package com.afrobad.VeinLinker.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class Users{
	
	// -------------------------------------------------------------------------
    // PRIMARY KEY — internal_userID
    // BIGINT AUTO_INCREMENT in MySQL → Long + @GeneratedValue in Java.
    // This column is NEVER exposed to the outside world.
    // It is used only for internal DB joins and foreign keys in other tables.
    // ------
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "internal_userID")
	private long internal_userID;
	
}
    
