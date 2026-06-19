//This repository will be resoponsible for save,create,read,update, delete user data in DB


package com.afrobad.VeinLinker.registrationandlogin.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
	
	// SELECT * FROM users WHERE email = ? LIMIT 1
	boolean existsByEmail(String email);
	
    //	SELECT * FROM users WHERE phone = ? LIMIT 1
	boolean existsByPhone(String phone);
	
	
	// For standard Email Login
    Optional<Users> findByEmail(String email);

    // For Phone Number Login (if supported)
    Optional<Users> findByPhone(String phone);

    // For Flexible Login (Allows user to type either their email OR phone number)
    Optional<Users> findByEmailOrPhone(String email, String phone);
    
    
    
 
    

}

// -------------------------------------------------------------------------
//// REGISTRATION CHECKS — FR-01 REQ-4
//// Used in Service layer BEFORE saving a new user to check uniqueness.
//// Spring Data auto-generates the SQL from the method name.
//// -------------------------------------------------------------------------
//

//
//// -------------------------------------------------------------------------
//// LOOKUP METHODS
//// Optional<> is used instead of Users directly — forces the caller
//// (Service layer) to handle the case where the user doesn't exist,
//// avoiding NullPointerExceptions.
//// -------------------------------------------------------------------------
//

//
//// Used for JWT token validation and inter-service references
//Optional<Users> findByEncryptedUserId(String encryptedUserId);
//
//// -------------------------------------------------------------------------
//// VERIFICATION UPDATES — FR-01 REQ-5
//// @Modifying + @Query used for targeted UPDATE statements.
//// Much more efficient than loading the full entity just to flip one boolean.
//// @Transactional is handled in the Service layer — NOT here.
//// -------------------------------------------------------------------------
//
//// Called by EmailVerificationService after user clicks email link
//@Modifying
//@Query("UPDATE Users u SET u.emailVerified = true WHERE u.internalUserId = :id")
//void markEmailVerified(@Param("id") Long internalUserId);
//
//// Called by OtpService after user enters correct SMS OTP
//@Modifying
//@Query("UPDATE Users u SET u.phoneVerified = true WHERE u.internalUserId = :id")
//void markPhoneVerified(@Param("id") Long internalUserId);
//
//// Called by Admin Dashboard after admin approves NID image
//@Modifying
//@Query("UPDATE Users u SET u.nidVerified = true WHERE u.internalUserId = :id")
//void markNidVerified(@Param("id") Long internalUserId);
//
//// -------------------------------------------------------------------------
//// ACCOUNT ACTIVATION — FR-01 REQ-5
//// Called by the Service layer after checking all 3 flags are true.
//// Sets both isVerified = true AND accountStatus = ACTIVE in one query.
//// -------------------------------------------------------------------------
//@Modifying
//@Query("""
//      UPDATE Users u
//      SET u.isVerified = true,
//          u.accountStatus = com.afrobad.VeinLinker.users.Users$AccountStatus.ACTIVE
//      WHERE u.internalUserId = :id
//        AND u.emailVerified = true
//        AND u.phoneVerified = true
//        AND u.nidVerified = true
//      """)
//void activateAccountIfFullyVerified(@Param("id") Long internalUserId);
//
//// -------------------------------------------------------------------------
//// PUBLIC ID GENERATION HELPER — called during registration
//// Finds the highest existing internalUserId to help generate next public ID
//// e.g. "VL-0000001", "VL-0000002" etc.
//// -------------------------------------------------------------------------
//@Query("SELECT MAX(u.internalUserId) FROM Users u")
//Optional<Long> findMaxInternalUserId();