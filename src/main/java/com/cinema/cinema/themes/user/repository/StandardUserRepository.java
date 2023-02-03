package com.cinema.cinema.themes.user.repository;

import com.cinema.cinema.themes.user.model.StandardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardUserRepository extends JpaRepository<StandardUser, Long> {

}
