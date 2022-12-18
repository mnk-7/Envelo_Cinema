package com.cinema.cinema.repositories;

import com.cinema.cinema.models.Show;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class ShowRepository {

    public Optional<Show> findShowById(long id){
        return Optional.of(new Show());
    }

    public List<Show> findAllFutureShows(LocalDate date, LocalTime localTime){
        return new ArrayList<>();
    }

    public void create(Show show){}

    public void update(long id, Show show){}

    public void delete(long id){}

}
