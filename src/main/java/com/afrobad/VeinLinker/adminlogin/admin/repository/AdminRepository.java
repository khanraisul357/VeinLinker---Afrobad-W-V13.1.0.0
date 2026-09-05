package com.afrobad.VeinLinker.adminlogin.admin.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afrobad.VeinLinker.adminlogin.admin.entity.Admin;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{
	
	// SELECT * FROM Admin WHERE email = ? LIMIT 1
	boolean existsByEmail(String email);
	
	// For fetching Admin by email
    Optional<Admin> findByEmail(String email);

}
