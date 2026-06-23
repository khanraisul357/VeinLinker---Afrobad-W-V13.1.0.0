package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile,Long>{

}
