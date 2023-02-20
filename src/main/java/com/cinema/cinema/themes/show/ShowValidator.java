package com.cinema.cinema.themes.show;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class ShowValidator extends ValidatorService<Show> {

    private final ShowRepository showRepository;

    public ShowValidator(Validator validator, ShowRepository showRepository) {
        super(validator);
        this.showRepository = showRepository;
    }

    @Override
    public Show validateExists(long showId) {
        Optional<Show> show = showRepository.findById(showId);
        if (show.isEmpty()) {
            throw new ElementNotFoundException("Show with ID " + showId + " not found");
        }
        return show.get();
    }

    public void validateVenueAndDateTime(Show show) {
        List<Show> shows = showRepository.findAllByVenueAndDates(show.getVenue(), show.getStartDateTime(), show.getEndDateTime());
        if (!shows.isEmpty()) {
            throw new ArgumentNotValidException("Venue " + show.getVenue().getName() + " is not available between " + show.getStartDateTime() + " and " + show.getEndDateTime());
        }
    }

    public void validateCancellation(Show show, LocalDateTime currentDateTime) {
        validateNoTicketSold(show); //TODO tests
        validateNotInPast(show, currentDateTime);
        validateNotInNearFuture(show, currentDateTime);
    }

    private void validateNoTicketSold(Show show) {
        if (!show.getTickets().isEmpty()) {
            throw new ProcessingException("Tickets for this show have already been sold. The show cannot be cancelled");
        }
    }

    private void validateNotInPast(Show show, LocalDateTime currentDateTime) {
        if (show.getStartDateTime().isBefore(currentDateTime)) {
            throw new ProcessingException("The show is in the past. It cannot be cancelled");
        }
    }

    private void validateNotInNearFuture(Show show, LocalDateTime currentDateTime) {
        if (Period.between(LocalDate.now(), show.getStartDateTime().toLocalDate()).getDays() <= 1) {
            throw new ProcessingException("The show starts soon. It cannot be cancelled anymore");
        }
    }

    public void validateInFuture(Show show) {
        if (show.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new ProcessingException("You cannot buy a ticket for a show that is in the past");
        }
    }

}
