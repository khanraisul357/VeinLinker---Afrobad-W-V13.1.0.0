package com.afrobad.VeinLinker.registrationandlogin.userprofile.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.Files;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.FileType;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.Format;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.UploadStatus;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.FileRepository;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.UserFileRepository;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

import io.jsonwebtoken.io.IOException;
import org.apache.tika.Tika;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileImageUpdateService {
	
	@Autowired
    private UsersRepository usersRepository;
	
	@Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserFileRepository userFileRepository;

    public void updateProfileImage(String email,MultipartFile profileImage) throws java.io.IOException {

        // 1. Find authenticated user
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() ->new RuntimeException("User not found"));

        // 2. Validate uploaded file
        if (profileImage == null || profileImage.isEmpty()) {
            throw new IllegalArgumentException("Profile image is required");
        }

        // 3. Validate uploaded file

        String originalFileName = profileImage.getOriginalFilename();

        if (originalFileName == null || originalFileName.isBlank()) {
            throw new IllegalArgumentException("Invalid file name");
        }

        // Maximum allowed profile image size: 5 MB
        long maxFileSize = 5 * 1024 * 1024;

        if (profileImage.getSize() > maxFileSize) {
            throw new IllegalArgumentException("Profile image must not exceed 5 MB");
        }

        // Detect the ACTUAL file type from file content
        String detectedContentType;

        try {

            Tika tika = new Tika();

            detectedContentType =tika.detect(profileImage.getInputStream());

        } catch (IOException e) {

            throw new IllegalArgumentException("Unable to determine file type");
        }

        // Allow only JPEG and PNG
        if (!"image/jpeg".equals(detectedContentType)&& !"image/png".equals(detectedContentType)) {

            throw new IllegalArgumentException("Only JPG, JPEG and PNG images are allowed");
        }

        System.out.println("Original filename: " + originalFileName);

        System.out.println("Client content type: " + profileImage.getContentType());

        System.out.println("Detected content type: " + detectedContentType);

        // 4. Create Files entity
        Files file = Files.builder()
                .fileName(profileImage.getOriginalFilename())
                .originalFileName(profileImage.getOriginalFilename())
                .fileUrl(null)
                .fileFormat(getFileFormat(profileImage))
                .size(profileImage.getSize())
                .build();

        // 5. Save file metadata
        Files savedFile = fileRepository.save(file);

        // 6. Check whether user already has a profile image
        Optional<UserFile> existingProfileImage =userFileRepository.findByUserAndDocumentType(user,FileType.PROFILE_IMAGE);

        if (existingProfileImage.isPresent()) {

            // Existing UserFile
            UserFile userFile = existingProfileImage.get();

            // Replace old Files reference
            userFile.setFile(savedFile);

            userFile.setUploadStatus(
                    UploadStatus.UPLOADED
            );

            userFileRepository.save(userFile);

        } else {

            // No profile image exists yet
            UserFile userFile = UserFile.builder()
                    .user(user)
                    .file(savedFile)
                    .documentType(FileType.PROFILE_IMAGE)
                    .uploadStatus(UploadStatus.UPLOADED)
                    .build();

            userFileRepository.save(userFile);
        }
    }
    
    
    // =========================================================
    // Convert MultipartFile content type → Format enum
    // =========================================================
    private Format getFileFormat(MultipartFile file) throws java.io.IOException {

        String detectedContentType;

        try {
            Tika tika = new Tika();
            detectedContentType = tika.detect(file.getInputStream());
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to determine file format");
        }

        if ("image/jpeg".equalsIgnoreCase(detectedContentType)) {
            return Format.jpeg;
        }

        if ("image/png".equalsIgnoreCase(detectedContentType)) {
            return Format.png;
        }

        if ("application/pdf".equalsIgnoreCase(detectedContentType)) {
            return Format.pdf;
        }

        throw new IllegalArgumentException("Unsupported file format");
    }
    
}
