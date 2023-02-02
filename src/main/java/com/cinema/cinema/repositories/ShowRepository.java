package com.cinema.cinema.repositories;

import com.cinema.cinema.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findAllByStartDateTimeAfter(LocalDateTime dateTime);

    List<Show> findAllByStartDateTimeBetween(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

}
