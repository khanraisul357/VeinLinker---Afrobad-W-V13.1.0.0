package com.afrobad.VeinLinker.registrationandlogin.userprofile.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.Files;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.VerificationDocumentType;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.FileRepository;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository.UserFileRepository;
import com.afrobad.VeinLinker.registrationandlogin.userprofile.dto.ProfileDocumentUpdateRequestDTO;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileDocumentUpdateService {

	@Autowired
    private UsersRepository usersRepository;
	
	@Autowired
    private FileRepository fileRepository;
	
	@Autowired
    private UserFileRepository userFileRepository;
    

    public void updateDocument(String email,ProfileDocumentUpdateRequestDTO request) {

    	// 1. Find currently logged-in user
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        
        // 2. Validate document type
        if (request.getDocumentType() == null) {
            throw new IllegalArgumentException("Document type is required");
        }

        // 3. Make sure at least one image was provided
        if (isEmpty(request.getFrontImage()) && isEmpty(request.getBackImage())) {

            throw new IllegalArgumentException("At least one document image is required");
        }
        
       
        // 4. Update NID
        if (request.getDocumentType() == VerificationDocumentType.NID) {

            if (!isEmpty(request.getFrontImage())) {

                saveOrUpdateFile(user,FileType.NID_FRONT,request.getFrontImage());
            }

            if (!isEmpty(request.getBackImage())) {

                saveOrUpdateFile(
                        user,
                        FileType.NID_BACK,
                        request.getBackImage()
                );
            }
        }
        
        
        // 5. Update Passport
        if (request.getDocumentType() == VerificationDocumentType.PASSPORT) {

            if (!isEmpty(request.getFrontImage())) {

                saveOrUpdateFile(user,FileType.PASSPORT_FRONT,request.getFrontImage());
            }

            if (!isEmpty(request.getBackImage())) {

                saveOrUpdateFile(user,FileType.PASSPORT_BACK,request.getBackImage());
            }
        }

        // 6. Save changes
        usersRepository.save(user);

        
    }
    
    //method that returns true when file is empty or no file uploaded
    private boolean isEmpty(MultipartFile file) {

        return file == null || file.isEmpty();
    }
    
    
    private void saveOrUpdateFile(Users user,FileType fileType,MultipartFile multipartFile) {

        try {

            // Find existing UserFile
            UserFile userFile =userFileRepository.findByUserAndDocumentType(user,fileType).orElse(null);


            // Create new Files record
            Files file = Files.builder()
                    .fileName(multipartFile.getOriginalFilename())
                    .originalFileName( multipartFile.getOriginalFilename())
                    .fileFormat(getFileFormat(multipartFile))
                    .size(multipartFile.getSize())
                    .build();


            // Save file metadata
            Files savedFile = fileRepository.save(file);


            if (userFile == null) {

                // First time this document is uploaded

                userFile = UserFile.builder()
                        .user(user)
                        .file(savedFile)
                        .documentType(fileType)
                        .uploadStatus(UploadStatus.PENDING)
                        .build();

            } else {

                // User is replacing an existing document

                userFile.setFile(savedFile);

                // New document needs verification again
                userFile.setUploadStatus(UploadStatus.PENDING);
            }


            userFileRepository.save(userFile);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to upload document",
                    e
            );
        }
    }


    // =========================================================
    // Convert MultipartFile content type → Format enum
    // =========================================================
    private Format getFileFormat(MultipartFile file) {

        String contentType = file.getContentType();

        if ("image/jpeg".equalsIgnoreCase(contentType)) {
            return Format.jpeg;
        }

        if ("image/jpg".equalsIgnoreCase(contentType)) {
            return Format.jpg;
        }

        if ("image/png".equalsIgnoreCase(contentType)) {
            return Format.png;
        }

        if ("application/pdf".equalsIgnoreCase(contentType)) {
            return Format.pdf;
        }

        throw new IllegalArgumentException("Unsupported file format");
    }
}