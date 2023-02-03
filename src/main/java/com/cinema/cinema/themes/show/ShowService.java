package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.seat.model.Seat;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class ShowService {

    private final ShowRepository showRepository;

    public Show getShow(long id) {
        Optional<Show> show = showRepository.findById(id);
        if (show.isEmpty()) {
            throw new ShowException("Show with given ID not found");
        }
        return show.get();
    }

    public List<Show> getAllShows() {
        return showRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
    }

    public List<Show> getAllShowsForCurrentWeek() {
        //TODO - adjusting logic
        LocalDateTime dateTimeFrom = LocalDateTime.now();
        LocalDateTime dateTimeTo = dateTimeFrom.plusDays(7);
        return showRepository.findAllByStartDateTimeBetween(dateTimeFrom, dateTimeTo);
    }

    public void addShow(Venue venue, Movie content, LocalDateTime startDateTime, int breakAfter) {
        Show show = new Show();
        show.setVenue(venue);
        show.setMovie(content);
        show.setStartDateTime(startDateTime);
        show.setBreakAfterInMinutes(breakAfter);
        show.setTickets(new HashSet<>());
        showRepository.save(show);
    }

    public void editShow(long id, Venue venue, Movie content, LocalDateTime startDateTime, int breakAfter) {
        Show show = getShow(id);
        show.setVenue(venue);
        show.setMovie(content);
        show.setStartDateTime(startDateTime);
        show.setBreakAfterInMinutes(breakAfter);
        showRepository.save(show);
    }

    //TODO - exception hierarchy
    public void cancelShow(long id) {
        Show show = getShow(id);
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (show.getTickets().size() > 0) {
            throw new ShowException("Tickets for this show have already been sold. The show cannot be cancelled");
        }
        if (show.getStartDateTime().isAfter(currentDateTime)) {
            throw new ShowException("The show is in the past. It cannot be cancelled");
        }
        if (Period.between(show.getStartDateTime().toLocalDate(), LocalDate.now()).getDays() <= 1) {
            throw new ShowException("The show starts soon. It cannot be cancelled anymore");
        }
        showRepository.deleteById(id);
    }

    public void addTicket(long id, Ticket ticket) {
        Show show = getShow(id);
        show.getTickets().add(ticket);
        showRepository.save(show);
    }

    public void removeTicket(long id, Ticket ticket) {
        Show show = getShow(id);
        show.getTickets().remove(ticket);
        showRepository.save(show);
    }

    public Set<Ticket> getAllTickets(long id) {
        Show show = getShow(id);
        return show.getTickets();
    }

    public Set<Seat> getAllSeats(long id) {
        Show show = getShow(id);
        return show.getVenue().getSeats();
    }

}
