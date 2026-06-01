//This Class represent the entity that will be stored in cache memory for temporary.

package com.afrobad.VeinLinker.registrationandlogin.cache;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.afrobad.VeinLinker.registrationandlogin.cache.enums.*;
import com.afrobad.VeinLinker.registrationandlogin.users.enums.*;

import lombok.*;


/**
 * Temporary registration state stored in Redis.
 *
 * This object exists only until registration is completed.
 * After final submission, it is converted into:
 * - Users entity
 * - Files entity
 * - UserFile entity
 *
 * Then removed from Redis.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDraft {

    // ==========================================================
    // DRAFT METADATA
    // ==========================================================

    /**
     * Unique Redis draft identifier.
     * Example: REG-91A82B7C
     */
    private String draftId;

    /**
     * Current registration step.
     * 1 = Form 1 completed
     * 2 = Form 2 completed
     * 3 = Form 3 completed
     */
    private Integer currentStep;

    /**
     * Current draft status.
     */
    private RegistrationStatus registrationStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // ==========================================================
    // FORM 1
    // ==========================================================

    private Role role;

    private String fullName;

    private String email;

    private String phone;

    /**
     * Store BCrypt hash only.
     * Never store raw password.
     */
    private String passwordHash;

    // ==========================================================
    // FORM 2
    // ==========================================================

    private String fathersName;

    private String mothersName;

    private LocalDate dob;

    private Gender gender;

    private BigDecimal height;

    private BigDecimal weight;

    private Religion religion;

    private MaritalStatus maritalStatus;

    private BloodGroup bloodGroup;

    private RhFactor rhFactor;
    

    // ==========================================================
    // FORM 3
    // ==========================================================

    /**
     * Uploaded documents associated with this draft.
     */
    private List<UploadedDocumentDraft> documents;
}




