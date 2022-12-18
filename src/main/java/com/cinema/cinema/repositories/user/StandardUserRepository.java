package com.cinema.cinema.repositories.user;

import com.cinema.cinema.models.user.StandardUser;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class StandardUserRepository {

    public Optional<StandardUser> findStandardUserById(long id){
        return Optional.of(new StandardUser());
    }

    public List<StandardUser> findAllStandardUsers() {
        return new ArrayList<>();
    }

    public void update(long id, StandardUser standardUser){
    }

}
