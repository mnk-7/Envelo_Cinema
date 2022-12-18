package com.cinema.cinema.repositories.user;

import com.cinema.cinema.models.user.AdminUser;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//TODO
@Repository
public class AdminUserRepository {

    public Optional<AdminUser> findAdminUserById(long id){
        return Optional.of(new AdminUser());
    }

    public void update(long id, AdminUser adminUser){}

}
