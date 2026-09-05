package com.afrobad.VeinLinker.admin.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.afrobad.VeinLinker.admin.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long>{

}
