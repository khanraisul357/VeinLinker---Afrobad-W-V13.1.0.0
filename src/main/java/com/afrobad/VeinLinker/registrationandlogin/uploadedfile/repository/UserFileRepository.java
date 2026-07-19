package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;
import com.afrobad.VeinLinker.registrationandlogin.users.entity.Users;
import java.util.List;
import java.util.Optional;

public interface UserFileRepository extends JpaRepository<UserFile,Long>{
	
	Optional<List<UserFile>> findByUser(Users user);

}
