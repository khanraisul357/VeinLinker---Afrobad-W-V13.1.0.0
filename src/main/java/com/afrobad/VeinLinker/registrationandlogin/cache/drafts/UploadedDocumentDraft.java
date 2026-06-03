package com.afrobad.VeinLinker.registrationandlogin.cache.drafts;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.enums.*;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadedDocumentDraft {

    private String fileName;

    private String originalFileName;

    private String fileUrl;

    private Format fileFormat;

    private Long size;

    private FileType documentType;

    private UploadStatus uploadStatus;
}