package com.afrobad.VeinLinker.registrationandlogin.userverification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.UserVerification;

public interface UserVerificationRepository extends JpaRepository <UserVerification,Long> {

	
}
