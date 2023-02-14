package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<Show, Long> {

    List<Show> findAllByStartDateTimeAfter(LocalDateTime dateTime);

    List<Show> findAllByStartDateTimeBetween(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);

    @Query(value = "FROM Show as s WHERE s.venue = :venue AND ((s.startDateTime BETWEEN :dateTimeFrom AND :dateTimeTo) OR (s.endDateTime BETWEEN :dateTimeFrom AND :dateTimeTo))")
    List<Show> findAllByVenueAndDates(@Param("venue") Venue venue, @Param("dateTimeFrom") LocalDateTime dateTimeFrom, @Param("dateTimeTo") LocalDateTime dateTimeTo);

}
