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
	
	// For fetching Users by email
    Optional<Users> findByEmail(String email);

    // For fetching Users by phone number
    Optional<Users> findByPhone(String phone);

    // For Flexible Login (Allows user to type either their email OR phone number)
    Optional<Users> findByEmailOrPhone(String email, String phone);
    
    Optional<Users> findByPublicUserId(String publicUserId);
    
    
}





