package com.afrobad.VeinLinker.registrationandlogin.userverification.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.UserDocumentVerification;

public interface UserDocumentVerificationRepository extends JpaRepository<UserDocumentVerification,Long>{
	
	Optional<UserDocumentVerification> findByUser(Users user);

}
