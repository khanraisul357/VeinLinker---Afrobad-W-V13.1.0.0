package com.afrobad.VeinLinker.registrationandlogin.uploadedfile.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.afrobad.VeinLinker.registrationandlogin.uploadedfile.entity.Files;

public interface FileRepository extends JpaRepository<Files, Long>  {

}
