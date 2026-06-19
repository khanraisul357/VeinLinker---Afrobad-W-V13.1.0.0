package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity;

import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.UserDocumentVerification;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "userfiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userfile_ID")
    private Long userFileId;

    
    // Foreign Key --> Reference to internal_userID from Users table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ID", referencedColumnName = "internal_userID", nullable = false)
    private Users user;

    //One UserFile records belong to one file
    //Foreign Key --> Reference to file_ID from files table
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_ID", referencedColumnName = "file_ID", nullable = false)
    private Files file;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_verification_id", nullable = true) // Adjust column name and nullability as per your DB design
    private UserDocumentVerification documentVerification;
    
    // Meaning of this file in system
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false, 
            columnDefinition= "ENUM('PROFILE_IMAGE','NID_FRONT','NID_BACK','PASSPORT_FRONT','PASSPORT_BACK')")
    private FileType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false,
            columnDefinition="ENUM('UPLOADED','PENDING','NOT_SUPPORTED')")
    @Builder.Default
    private UploadStatus status = UploadStatus.PENDING;


}
