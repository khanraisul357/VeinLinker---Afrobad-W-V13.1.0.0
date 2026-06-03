package com.afrobad.VeinLinker.registrationandlogin.mapper;


import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.afrobad.VeinLinker.registrationandlogin.cache.drafts.RegistrationDraft;
import com.afrobad.VeinLinker.registrationandlogin.users.dto.*;




/*componentModel = "spring" allows to create object of this mapper and store it to IOC Container & inject it.
Simply I am telling spring to manage lifecycle of object of this mapper.

*unmappedTargetPolicy = ReportingPolicy.IGNORE tells MapStruct to tell compiler ignore unmatched fields. So that
* compiler don't show any compilation warnings for each unmapped fields.
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationMapper {

	

	
    RegistrationDraft form1RequestDtoToDraft(RegistrationForm1Request request);
    
    //Form 2Updates the EXISTNG draft loaded from Redis. It does NOT touch Form 1 data.
    void updateDraftWithForm2(RegistrationForm2Request request, @MappingTarget RegistrationDraft draft);

    // Form 3: Updates the EXISTING draft loaded from Redis with Form 3 data before final DB push.
    void updateDraftWithForm3(RegistrationForm3Request request, @MappingTarget RegistrationDraft draft);

    
}