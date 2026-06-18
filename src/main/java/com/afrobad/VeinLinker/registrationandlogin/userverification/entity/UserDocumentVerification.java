package com.afrobad.VeinLinker.registrationandlogin.userverification.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.afrobad.VeinLinker.registrationandlogin.userverification.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_document_verification") 
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDocumentVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "userdocumentverification_ID")
    private Long userDocumentVerificationId;

    // One User can go through multiple Document Verification cycles (e.g: if first one gets rejected)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "internal_userID", nullable = false)
    private Users userId; 
    
    // The admin account that processed the request
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id", referencedColumnName = "internal_userID")
    private Users reviewedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    // One verification request links to multiple files (e.g., NID Front and NID Back)
    // 'mappedBy' points to the field name inside the UserFile class that handles this relationship
    @OneToMany(mappedBy = "documentVerification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserFile> files;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DocumentVerificationStatus status;

    @CreationTimestamp
    @Column(name = "reviewed_at", nullable = false, updatable = false)
    private LocalDateTime reviewedAt;
}