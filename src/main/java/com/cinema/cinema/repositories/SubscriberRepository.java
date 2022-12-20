package com.cinema.cinema.repositories;

import com.cinema.cinema.models.Subscriber;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class SubscriberRepository {

    public Optional<Subscriber> findSubscriberByEmail(String email){
        return Optional.of(new Subscriber());
    }

    public List<Subscriber> findAllSubscribers(){
        return new ArrayList<>();
    }

    public void create(String email){}

    public void delete(String email){}

}
