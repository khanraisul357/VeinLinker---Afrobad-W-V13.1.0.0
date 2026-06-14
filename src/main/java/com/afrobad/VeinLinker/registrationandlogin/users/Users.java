package com.afrobad.VeinLinker.registrationandlogin.users;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.afrobad.VeinLinker.registrationandlogin.users.enums.*;


import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Entity
@Table(name="users")
@Getter // Generates all getters
@Setter // Generates all setters
@NoArgsConstructor // Required by JPA
@AllArgsConstructor // Required by @Builder
@Builder // Allows for Users.builder().fullName("...").build()
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
	private long internalUserId;
	
	// -------------------------------------------------------------------------
    // encrypted_userID — CHAR(36), maps to a UUID string (e.g. "550e8400-e29b-...")
    // Generated once at registration in the Service layer using UUID.randomUUID().
    // Used for inter-service communication and in JWT tokens.
    // nullable = false means Hibernate will enforce NOT NULL at the ORM level too,
    // matching your SQL column constraint.
    // unique = true mirrors the UNIQUE constraint in your SQL table.
    // -------------------------------------------------------------------------
    @Column(name = "encrypted_userID", nullable = false, unique = true, length = 36,
            columnDefinition = "CHAR(36)")
    private String encryptedUserId;

    // -------------------------------------------------------------------------
    // public_userID — VARCHAR(15), human-readable ID shown in the UI.
    // Example format: "VL-0000001". Generated in the Service layer.
    // Never auto-incremented by MySQL — your Service builds this string
    // after save so it can use the internalUserId number inside it.
    // -------------------------------------------------------------------------
    @Column(name = "public_userID", nullable = false, unique = true, length = 15)
    private String publicUserId;

    // =========================================================================
    // STEP 1 REGISTRATION FIELDS — FR-01 REQ-1
    // Captured in the first registration form page.
    // =========================================================================

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;
    
    @Column(name = "full_name", nullable = false)
    private String fullName;

    // Email must be unique across all users — FR-01 REQ-4
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // Phone must be unique across all users — FR-01 REQ-4
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    // This field stores the BCrypt hash of the password, NEVER the plain text.
    // The Service layer calls passwordEncoder.encode(rawPassword) before saving.
    @Column(name = "password", nullable = false)
    private String password;

    // =========================================================================
    // STEP 2 REGISTRATION FIELDS — FR-01 REQ-2
    // Captured in the second registration form page.
    // =========================================================================

    @Column(name = "fathers_name", nullable = false)
    private String fathersName;

    @Column(name = "mothers_name", nullable = false)
    private String mothersName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dob;
    
    @Transient
    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private int age;
    
    public int getAge() {
        if (this.dob != null) {
            return Period.between(this.dob, LocalDate.now()).getYears();
        }
        return 0;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false,
            columnDefinition = "ENUM('MALE','FEMALE','OTHER')")
    private Gender gender;

    @Column(name = "height", nullable = false, precision = 5, scale = 2)
    private BigDecimal height;

    @Column(name = "weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(name="religion", nullable = false,
    		columnDefinition = "Enum('CHRISTIAN','ISLAM','HINDU','JEW','BUDHISTS','SIKH','OTHER')")
    private Religion religion;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false,
            columnDefinition = "ENUM('SINGLE','MARRIED','DIVORCED','WIDOWED')")
    private MaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group",columnDefinition = "ENUM('A','B','AB','O')")
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "rh_factor",
            columnDefinition = "ENUM('POSITIVE','NEGATIVE')")
    private RhFactor rhFactor;

   
    
    @Enumerated(EnumType.STRING)
    @Column(name = "is_verified", nullable = false,
            columnDefinition = "ENUM('FORM1_COMPLETED','FORM2_COMPLETED','FORM3_COMPLETED','PENDING','VERIFIED','REJECTED')")
    @Builder.Default
    private UserStatus isVerified=UserStatus.PENDING;
    
    
    public enum AccountStatus {
        ACTIVE, INACTIVE, SUSPENDED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false,columnDefinition = "ENUM('ACTIVE','INACTIVE', 'SUSPENDED')" )
    @Builder.Default
    private AccountStatus accountStatus = AccountStatus.INACTIVE;

    // -------------------------------------------------------------------------
    // lastActiveMode — FR-02 REQ-5 (saves which dashboard loaded last).
    // Can be null on first login before the user has ever toggled.
    // -------------------------------------------------------------------------
    public enum ActiveMode {
        DONOR, RECEIVER
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "last_active_mode")
    private ActiveMode lastActiveMode;

    // -------------------------------------------------------------------------
    // Portal stats fields — FR-02 REQ-4 and REQ-5.
    // donationCount  → how many times this user has donated blood.
    // receivingCount → how many times this user has received blood.
    // points         → earned through successful interactions.
    // level          → computed from points in the Service layer.
    // reliabilityScore → decremented when donor fails Liveliness Check (FR-08).
    // All default to 0 — a brand-new user starts with nothing.
    // -------------------------------------------------------------------------
    @Column(name = "donation_count", nullable = false)
    @Builder.Default
    private int donationCount = 0;

    @Column(name = "receiving_count", nullable = false)
    @Builder.Default
    private int receivingCount = 0;

    @Column(name = "points", nullable = false)
    @Builder.Default
    private int points = 0;

    @Column(name = "level", nullable = false)
    @Builder.Default
    private int level = 0;

    @Column(name = "reliability_score", nullable = false)
    @Builder.Default
    private int reliabilityScore = 100;

    // -------------------------------------------------------------------------
    // created_at — TIMESTAMP DEFAULT CURRENT_TIMESTAMP.
    // @CreationTimestamp tells Hibernate to set this automatically on INSERT.
    // updatable = false means Hibernate will never change it after creation.
    // LocalDateTime maps to MySQL TIMESTAMP.
    // -------------------------------------------------------------------------
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    
}
    




