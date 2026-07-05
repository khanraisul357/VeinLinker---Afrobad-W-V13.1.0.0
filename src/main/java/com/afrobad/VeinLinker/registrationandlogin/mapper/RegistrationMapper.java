package com.afrobad.VeinLinker.registrationandlogin.mapper;

import java.util.UUID;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.UploadedFileDraft;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.Files;
import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import lombok.*;

/*
*componentModel = "spring" allows to create object of this mapper and store it to IOC Container & inject it.
*Simply I am telling spring to manage lifecycle of object of this mapper.
*unmappedTargetPolicy = ReportingPolicy.IGNORE tells MapStruct to tell compiler ignore unmatched fields. So that
*compiler don't show any compilation warnings for each unmapped fields.
*/

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationMapper {

	@Mapping(target = "uploadedDocuments", ignore = true)
    RegistrationDraft form1RequestDtoToDraft(RegistrationForm1Request request);
    
    //Form 2Updates the EXISTNG draft loaded from Redis. It does NOT touch Form 1 data.
    void updateDraftWithForm2(RegistrationForm2Request request, @MappingTarget RegistrationDraft draft);

    // Form 3: Updates the EXISTING draft loaded from Redis with Form 3 data before final DB push.
    default UploadedFileDraft MultipartFileToUploadedDocumentDraft(MultipartFile file, FileType fileType, Format format) {
    	
    	return UploadedFileDraft.builder()

                .fileName(UUID.randomUUID().toString())

                .originalFileName(file.getOriginalFilename())

                .size(file.getSize())

                .fileFormat(format)

                .documentType(fileType)

                .uploadStatus(UploadStatus.PENDING)

                .fileUrl(null)

                .build();
    	
    }
    
    // ======================================================
    // RegistrationDraft To Users
    // ======================================================
   
    //Telling mapstruct not to map fields which are not same as source(RegistrationDraft) & ignore them
  
    @Mapping(source = "passwordHashFormat", target = "password")
    Users draftToUsers(RegistrationDraft draft);
    
    // ======================================================
    // RegistrationDraft To Files
    // ======================================================
    
    Files draftToFiles(UploadedFileDraft fileDraft);

    
}