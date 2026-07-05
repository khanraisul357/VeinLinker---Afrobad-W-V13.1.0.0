//This repository responsible to store OTPVerification entity to DB

package com.afrobad.VeinLinker.registrationandlogin.userverification.repository;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import com.afrobad.VeinLinker.registrationandlogin.userverification.entity.OTPVerification;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPVerificationRepository extends JpaRepository<OTPVerification,Long> {

	Optional<OTPVerification> findByUser(Users user);

}
