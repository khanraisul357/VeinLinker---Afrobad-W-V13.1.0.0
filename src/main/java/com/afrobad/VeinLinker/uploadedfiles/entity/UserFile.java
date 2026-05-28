package com.afrobad.VeinLinker.uploadedfiles.entity;

import com.afrobad.VeinLinker.users.Users;

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
    private Long ID;

    
    // Foreign Key --> Reference to internal_userID from Users table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", referencedColumnName = "internal_userID", nullable = false)
    private Users userId;

    //Foreign Key --> Reference to file_ID from files table
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userfileID", referencedColumnName = "file_ID", nullable = false)
    private Files file;

    // Meaning of this file in system
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private FileStatus status = FileStatus.UPLOADED;

    public enum DocumentType {
        PROFILE_IMAGE,
        NID_FRONT,
        NID_BACK,
        PASSPORT
    }

    public enum FileStatus {
        UPLOADED,
        VERIFIED,
        REJECTED
    }
}
