package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity;

import jakarta.persistence.*;


import lombok.*;
import java.time.LocalDateTime;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import org.hibernate.annotations.CreationTimestamp;
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

    @Column(nullable = true)
    private String fileUrl; // accessible URL (if applicable)

    @Enumerated(EnumType.STRING)
    @Column(name="format",nullable = false,columnDefinition = "ENUM('jpg','jpeg','png','pdf')")
    private Format fileFormat; // IMAGE, PDF, etc.


    @Column(nullable=false)
    private Long size;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    
}   