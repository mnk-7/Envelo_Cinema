package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.content.model.Content;
import com.cinema.cinema.themes.content.validator.ContentValidator;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.venue.VenueValidator;
import com.cinema.cinema.themes.venue.model.Venue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowValidator showValidator;
    private final ContentValidator contentValidator;
    private final VenueValidator venueValidator;

    @Transactional(readOnly = true)
    public Show getShow(long showId) {
        Show show = showValidator.validateExists(showId);
        show.setEndDateTime(calculateEndDateTime(show));
        return show;
    }

    @Transactional(readOnly = true)
    public List<Show> getAllShows() {
        return showRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public List<Show> getAllShowsForCurrentWeek() {
        LocalDateTime dateTimeFrom = LocalDateTime.now();
        LocalDateTime dateTimeTo = setDateTimeTo(dateTimeFrom);
        return showRepository.findAllByStartDateTimeBetween(dateTimeFrom, dateTimeTo);
    }

    @Transactional
    public Show addShow(Show showFromDto) {
        showValidator.validateInput(showFromDto);
        Show show = createShow(showFromDto);
        showValidator.validateVenueAndDateTime(show);
        return showRepository.save(show);
    }

    @Transactional
    public void editShow(long showId, Show showFromDto) {
        Show show = showValidator.validateExists(showId);
        showValidator.validateInput(showFromDto);
        setFields(show, showFromDto);
        showValidator.validateVenueAndDateTime(show);
        showRepository.save(show);
    }

    @Transactional
    public void cancelShow(long showId) {
        Show show = showValidator.validateExists(showId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        showValidator.validateCancellation(show, currentDateTime);
        showRepository.deleteById(showId);

        //działa bez nullowania venue i content;
        //delete all tickets nie jest konieczne z uwagi na walidację,
        //że show można usunąć jedynie, jeśli nie ma żadnego ticketu
    }

    @Transactional
    public void addTicket(Show show, Ticket ticket) {
        show.getTickets().add(ticket);
        showRepository.save(show);
    }

    @Transactional
    public void removeTicket(Show show, Ticket ticket) {
        show.getTickets().remove(ticket);
        showRepository.save(show);
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllTickets(long showId) {
        Show show = showValidator.validateExists(showId);
        return show.getTickets().stream().toList();
    }

    @Transactional(readOnly = true)
    public Show getShowWithVenueDetails(long showId) {
        return showValidator.validateExists(showId);
    }

    private LocalDateTime setDateTimeTo(LocalDateTime dateTimeFrom) {
        DayOfWeek currentDayOfWeek = dateTimeFrom.getDayOfWeek();
        int daysDifference = DayOfWeek.SUNDAY.ordinal() - currentDayOfWeek.ordinal();
        return dateTimeFrom.plusDays(daysDifference);
    }

    private LocalDateTime calculateEndDateTime(Show show) {
        LocalDateTime startDateTime = show.getStartDateTime();
        int duration = show.getContent().getDurationInMinutes() + show.getBreakAfterInMinutes();
        return startDateTime.plusMinutes(duration);
    }

    private Show createShow(Show showFromDto) {
        Show show = new Show();
        Content content = contentValidator.validateExists(showFromDto.getContent().getId());
        show.setContent(content);
        setFields(show, showFromDto);
        return show;
    }

    private void setFields(Show show, Show showFromDto) {
        Venue venue = venueValidator.validateExists(showFromDto.getVenue().getId());
        venueValidator.validateIsActive(venue);
        show.setVenue(venue);
        show.setStartDateTime(showFromDto.getStartDateTime());
        show.setBreakAfterInMinutes(showFromDto.getBreakAfterInMinutes());
        show.setEndDateTime(calculateEndDateTime(show));
    }

}
