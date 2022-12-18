package com.cinema.cinema.repositories;

import com.cinema.cinema.models.categories.TicketType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class TicketTypeRepository {

    public Optional<TicketType> findTicketTypeById(long id){
        return Optional.of(new TicketType());
    }

    public List<TicketType> findAllTicketTypes(){
        return new ArrayList<>();
    }

    public List<TicketType> findAllActiveTicketTypes(){
        return new ArrayList<>();
    }

    public void create(TicketType ticketType){
    }

    public void update(long id, TicketType ticketType){
    }

}
