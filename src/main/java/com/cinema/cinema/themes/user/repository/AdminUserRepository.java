package com.cinema.cinema.themes.user.repository;

import com.cinema.cinema.themes.user.model.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {

}
