package com.afrobad.VeinLinker.registrationandlogin.userverification.entity;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "otp_verifications")
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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID", referencedColumnName = "internal_userID", nullable = false)
    private Users user;

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private boolean isEmailVerified = false;
    
    @Column(name = "is_number_verified", nullable = false)
    @Builder.Default
    private boolean isNumberVerified = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Automatically set the creation timestamp before saving
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
    
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;
    
    // Quick helper method to check if the OTP has expired
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}