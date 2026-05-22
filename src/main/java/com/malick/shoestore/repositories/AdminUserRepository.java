package com.malick.shoestore.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.malick.shoestore.entities.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

    Optional<AdminUser> findByEmail(String email);
}
