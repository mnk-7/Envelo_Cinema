package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.ShowException;
import com.cinema.cinema.models.Show;
import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.content.Content;
import com.cinema.cinema.repositories.ShowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private ShowRepository showRepository;

    public Show getShow(long id) {
        Optional<Show> show = showRepository.findShowById(id);
        if (show.isEmpty()) {
            throw new ShowException("Show with given ID not found");
        }
        return show.get();
    }

    public List<Show> getAllShows() {
        return showRepository.findAllFutureShows(LocalDate.now(), LocalTime.now());
    }

    public void addShow(Venue venue, Content content, LocalDate date, LocalTime startTime, int breakAfter) {
        Show show = new Show();
        show.setVenue(venue);
        show.setContent(content);
        show.setDate(date);
        show.setStartTime(startTime);
        show.setBreakAfter(breakAfter);
        show.setTickets(new HashSet<>());
        showRepository.create(show);
    }

    public void editShow(long id, Venue venue, Content content, LocalDate date, LocalTime startTime, int breakAfter) {
        Show show = getShow(id);
        show.setVenue(venue);
        show.setContent(content);
        show.setDate(date);
        show.setStartTime(startTime);
        show.setBreakAfter(breakAfter);
        showRepository.update(id, show);
    }

    public void cancelShow(long id) {
        Show show = getShow(id);
        LocalDate currentDate = LocalDate.now();
        if (show.getTickets().size() > 0) {
            throw new ShowException("Tickets for this show have already been sold. The show cannot be cancelled");
        }
        if (show.getDate().isAfter(currentDate)) {
            throw new ShowException("The show is in the past. It cannot be cancelled");
        }
        if (Period.between(show.getDate(), LocalDate.now()).getDays() <= 1) {
            throw new ShowException("The show starts soon. It cannot be cancelled anymore");
        }
        showRepository.delete(id);
    }

}
