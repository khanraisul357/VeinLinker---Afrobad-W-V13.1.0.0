package com.afrobad.VeinLinker.registrationandlogin.userverification.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationDocumentStatus;
import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.VerificationStage;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userverification_ID")
    private Long userVerificationId;

    //One User go through one Verification
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "internal_userID", nullable = false)
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = false,
            columnDefinition = "ENUM('EMAIL_PENDING','PHONENUMBER_PENDING','DOCUMENT_PENDING','COMPLETED')")
    private VerificationStage stage;

    @Column(name = "email_verified", nullable = false)
    @Builder.Default 
    private boolean emailVerified = false;

    @Column(name = "phone_verified", nullable = false)
    @Builder.Default
    private boolean phoneVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_status", nullable = false)
    @Builder.Default
    private VerificationDocumentStatus documentStatus=VerificationDocumentStatus.PENDING;

    @CreationTimestamp
    @Column(name = "verified_at", nullable = false, updatable = false)
    private LocalDateTime verifiedAt;
}
