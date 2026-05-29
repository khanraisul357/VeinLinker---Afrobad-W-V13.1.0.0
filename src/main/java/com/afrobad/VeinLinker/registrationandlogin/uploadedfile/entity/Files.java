package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.afrobad.VeinLinker.registrationandlogin.users.Users;

@Entity
@Table(name = "files")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_ID")
    private Long fileId;

    @Column(nullable = false)
    private String fileName; // stored/generated name

    @Column(nullable = false)
    private String originalFileName; // user uploaded name

    @Column(nullable = false)
    private String fileUrl; // accessible URL (if applicable)

    @Column(nullable = false)
    private String fileType; // IMAGE, PDF, etc.

    private String mimeType;

    private Long size;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

 // Replace the plain Long with this:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uploaded_by", referencedColumnName = "internal_userID", nullable = false)
    private Users uploadedBy; // userId reference (no direct FK to User entity)
}