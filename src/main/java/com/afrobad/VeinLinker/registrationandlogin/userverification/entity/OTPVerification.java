package com.afrobad.VeinLinker.registrationandlogin.userverification.entity;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_otp_verifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OTPVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "otp_ID")
    private Long otpId;

    // Direct link to the User this OTP was generated for
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID", referencedColumnName = "internal_userID", nullable = false)
    private Users userId;

    // The hashed or plain text OTP string (usually 6 digits)
    @Column(name = "otp_code", nullable = false, length = 6)
    private String otpCode;

    // Track where it was sent, making it reusable for both paths
    @Column(name = "verification_target", nullable = false)
    private String verificationTarget; // Stores the specific email or phone number used

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "is_used", nullable = false)
    @Builder.Default
    private boolean isUsed = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before saving
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    // Quick helper method to check if the OTP has expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}