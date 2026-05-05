package com.afrobad.VeinLinker.users;

//importing enums
import com.afrobad.VeinLinker.common_enums.Gender;
import com.afrobad.VeinLinker.common_enums.MaritalStatus;
import com.afrobad.VeinLinker.common_enums.BloodGroup;
import com.afrobad.VeinLinker.common_enums.RhFactor;



import jakarta.persistence.*;
import lombok.*;


import org.hibernate.annotations.CreationTimestamp;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    // LocalDate maps perfectly to MySQL DATE column (no time component needed).
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    

    // -------------------------------------------------------------------------
    // ENUM fields — @Enumerated(EnumType.STRING) tells Hibernate to store
    // the enum NAME (e.g. "MALE") as a string in the DB, not the ordinal number.
    // We use EnumType.STRING always — if you add values later, ordinal numbers
    // shift and corrupt all existing data.
    
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false,
            columnDefinition = "ENUM('MALE','FEMALE','OTHER')")
    private Gender gender;

    // DECIMAL(5,2) in MySQL → BigDecimal in Java.
    // Using BigDecimal (not double/float) because it is exact — critical for
    // medical data like height and weight where precision matters.
    @Column(name = "height", nullable = false, precision = 5, scale = 2)
    private BigDecimal height;

    @Column(name = "weight", nullable = false, precision = 5, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status", nullable = false,
            columnDefinition = "ENUM('SINGLE','MARRIED','DIVORCED','WIDOWED')")
    private MaritalStatus maritalStatus;

    // blood_group and rh_factor have NO nullable = false because your SQL table
    // has no NOT NULL constraint on them — they are nullable (user can fill later).
    @Enumerated(EnumType.STRING)
    @Column(name = "blood_group",
            columnDefinition = "ENUM('A','B','AB','O')")
    private BloodGroup bloodGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "rh_factor",
            columnDefinition = "ENUM('POSITIVE','NEGATIVE')")
    private RhFactor rhFactor;

    // =========================================================================
    // STEP 3 REGISTRATION FIELDS — FR-01 REQ-3
    // Store the URL/path returned after uploading to your storage (local or S3).
    // The actual file is NEVER stored in the database.
    // =========================================================================

    @Column(name = "nid_image_url", nullable = false)
    private String nidImageUrl;

    @Column(name = "profile_image_url", nullable = false)
    private String profileImageUrl;

    // =========================================================================
    // SYSTEM-MANAGED FIELDS — set by the backend, never by the user directly.
    // =========================================================================

    // -------------------------------------------------------------------------
    // is_verified — FR-01 REQ-5
    // Maps to BOOLEAN NOT NULL DEFAULT FALSE.
    // This becomes TRUE only when ALL three conditions below are true:
    //   1. emailVerified = true  (user clicked email link)
    //   2. phoneVerified = true  (user entered SMS OTP correctly)
    //   3. nidVerified = true    (admin manually approved NID image)
    // The Service layer checks all three flags and flips this field.
    // Displayed as the Verification Status Badge in FR-02 REQ-3.
    // -------------------------------------------------------------------------
    @Column(name = "is_verified", nullable = false)
    private boolean isVerified = false;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;

    @Column(name = "phone_verified", nullable = false)
    private boolean phoneVerified = false;

    @Column(name = "nid_verified", nullable = false)
    private boolean nidVerified = false;

    // -------------------------------------------------------------------------
    // accountStatus — tracks the user's lifecycle state.
    // PENDING  → just registered, not yet fully verified.
    // ACTIVE   → all verifications complete, can log in normally.
    // SUSPENDED→ admin has suspended the account (shadow ban from FR-08 REQ-13).
    // Add this column to your SQL table via Flyway migration.
    // -------------------------------------------------------------------------
    
    public enum AccountStatus {
        PENDING, ACTIVE, SUSPENDED
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.PENDING;

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
    private int donationCount = 0;

    @Column(name = "receiving_count", nullable = false)
    private int receivingCount = 0;

    @Column(name = "points", nullable = false)
    private int points = 0;

    @Column(name = "level", nullable = false)
    private int level = 0;

    @Column(name = "reliability_score", nullable = false)
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

    

    // =========================================================================
    // CONSTRUCTORS
    // A no-arg constructor is REQUIRED by JPA — Hibernate uses it to
    // reconstruct objects when it reads rows from the database.
    // Without it, Hibernate will throw an InstantiationException at runtime.
    // =========================================================================

    public Users() {
    }

    // =========================================================================
    // GETTERS AND SETTERS
    // Plain getters and setters — no @Data or Lombok used here deliberately,
    // because Lombok's @EqualsAndHashCode can cause infinite loops with
    // bidirectional JPA relationships in future when you add @OneToMany fields.
    // =========================================================================

//    public Long getInternalUserId() { return internalUserId; }
//
//    public void setEncryptedUserId(String encryptedUserId) { this.encryptedUserId = encryptedUserId; }
//    public String getEncryptedUserId() { return encryptedUserId; }
//
//    public void setPublicUserId(String publicUserId) { this.publicUserId = publicUserId; }
//    public String getPublicUserId() { return publicUserId; }
//
//    public void setFullName(String fullName) { this.fullName = fullName; }
//    public String getFullName() { return fullName; }
//
//    public void setEmail(String email) { this.email = email; }
//    public String getEmail() { return email; }
//
//    public void setPhone(String phone) { this.phone = phone; }
//    public String getPhone() { return phone; }
//
//    public void setPassword(String password) { this.password = password; }
//    public String getPassword() { return password; }
//
//    public void setFathersName(String fathersName) { this.fathersName = fathersName; }
//    public String getFathersName() { return fathersName; }
//
//    public void setMothersName(String mothersName) { this.mothersName = mothersName; }
//    public String getMothersName() { return mothersName; }
//
//    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
//    public LocalDate getDateOfBirth() { return dateOfBirth; }
//
//    public void setGender(Gender gender) { this.gender = gender; }
//    public Gender getGender() { return gender; }
//
//    public void setHeight(BigDecimal height) { this.height = height; }
//    public BigDecimal getHeight() { return height; }
//
//    public void setWeight(BigDecimal weight) { this.weight = weight; }
//    public BigDecimal getWeight() { return weight; }
//
//    public void setMaritalStatus(MaritalStatus maritalStatus) { this.maritalStatus = maritalStatus; }
//    public MaritalStatus getMaritalStatus() { return maritalStatus; }
//
//    public void setBloodGroup(BloodGroup bloodGroup) { this.bloodGroup = bloodGroup; }
//    public BloodGroup getBloodGroup() { return bloodGroup; }
//
//    public void setRhFactor(RhFactor rhFactor) { this.rhFactor = rhFactor; }
//    public RhFactor getRhFactor() { return rhFactor; }
//
//    public void setNidImageUrl(String nidImageUrl) { this.nidImageUrl = nidImageUrl; }
//    public String getNidImageUrl() { return nidImageUrl; }
//
//    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }
//    public String getProfileImageUrl() { return profileImageUrl; }
//
//    public void setVerified(boolean verified) { isVerified = verified; }
//    public boolean isVerified() { return isVerified; }
//
//    public void setEmailVerified(boolean emailVerified) { this.emailVerified = emailVerified; }
//    public boolean isEmailVerified() { return emailVerified; }
//
//    public void setPhoneVerified(boolean phoneVerified) { this.phoneVerified = phoneVerified; }
//    public boolean isPhoneVerified() { return phoneVerified; }
//
//    public void setNidVerified(boolean nidVerified) { this.nidVerified = nidVerified; }
//    public boolean isNidVerified() { return nidVerified; }
//
//    public void setAccountStatus(AccountStatus accountStatus) { this.accountStatus = accountStatus; }
//    public AccountStatus getAccountStatus() { return accountStatus; }
//
//    public void setLastActiveMode(ActiveMode lastActiveMode) { this.lastActiveMode = lastActiveMode; }
//    public ActiveMode getLastActiveMode() { return lastActiveMode; }
//
//    public void setDonationCount(int donationCount) { this.donationCount = donationCount; }
//    public int getDonationCount() { return donationCount; }
//
//    public void setReceivingCount(int receivingCount) { this.receivingCount = receivingCount; }
//    public int getReceivingCount() { return receivingCount; }
//
//    public void setPoints(int points) { this.points = points; }
//    public int getPoints() { return points; }
//
//    public void setLevel(int level) { this.level = level; }
//    public int getLevel() { return level; }
//
//    public void setReliabilityScore(int reliabilityScore) { this.reliabilityScore = reliabilityScore; }
//    public int getReliabilityScore() { return reliabilityScore; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
	
}
    
