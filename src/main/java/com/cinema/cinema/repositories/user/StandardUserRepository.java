package com.cinema.cinema.repositories.user;

import com.cinema.cinema.models.user.StandardUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StandardUserRepository extends JpaRepository<StandardUser, Long> {

}
