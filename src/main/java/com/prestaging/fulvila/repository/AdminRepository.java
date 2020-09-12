package com.prestaging.fulvila.repository;

import com.prestaging.fulvila.model.Admin;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Integer> {
	
	@Query(value = "SELECT * FROM fulvila_admins a WHERE a.email = ?1 AND a.password = ?2", nativeQuery = true)
	List<Admin> findByEmailAndPassword(String email, String password);

	@Query(value = "SELECT * FROM fulvila_admins a WHERE a.email = ?1", nativeQuery = true)
	Admin findByEmail(String email);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM fulvila_admins WHERE email = ?1 AND password = ?2", nativeQuery = true)
	int deleteByEmailAndPassword(String email, String password);
}