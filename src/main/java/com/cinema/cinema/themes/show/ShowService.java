package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.content.MovieValidator;
import com.cinema.cinema.themes.content.model.Content;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.show.model.ShowInputDto;
import com.cinema.cinema.themes.show.model.ShowOutputShortDto;
import com.cinema.cinema.themes.venue.VenueValidator;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.List;

@AllArgsConstructor
@Service
public class ShowService {

    private final ShowRepository showRepository;
    private final ShowValidator showValidator;
    private final MovieValidator movieValidator;
    private final VenueValidator venueValidator;
    private DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public ShowOutputShortDto getShow(long showId) {
        Show show = showValidator.validateExists(showId);
        show.setEndDateTime(calculateEndDateTime(show));
        return mapperService.mapToShowShortDto(show);
    }

    @Transactional(readOnly = true)
    public List<ShowOutputShortDto> getAllShows() {
        List<Show> shows = showRepository.findAllByStartDateTimeAfter(LocalDateTime.now());
        return shows.stream()
                .peek(show -> show.setEndDateTime(calculateEndDateTime(show)))
                .map(mapperService::mapToShowShortDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShowOutputShortDto> getAllShowsForCurrentWeek() {
        LocalDateTime dateTimeFrom = LocalDateTime.now();
        LocalDateTime dateTimeTo = setDateTimeTo(dateTimeFrom);
        List<Show> shows = showRepository.findAllByStartDateTimeBetween(dateTimeFrom, dateTimeTo);
        return shows.stream()
                .peek(show -> show.setEndDateTime(calculateEndDateTime(show)))
                .map(mapperService::mapToShowShortDto)
                .toList();
    }

    @Transactional
    public ShowOutputShortDto addShow(ShowInputDto showDto) {
        Show showFromDto = mapperService.mapToShow(showDto);
        showValidator.validateInput(showFromDto);
        Show show = createShow(showFromDto);
        showRepository.save(show);
        return mapperService.mapToShowShortDto(show);
    }

    @Transactional
    public void editShow(long showId, ShowInputDto showDto) {
        Show show = showValidator.validateExists(showId);
        Show showFromDto = mapperService.mapToShow(showDto);
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
        //TODO sprawdzić, czy trzeba zerować venue i content - nie
        //delete all tickets -> przy czym usunąć show można jedynie, jeśli nie ma żadnego
        showRepository.deleteById(showId);
    }

//    public void addTicket(long showId, Ticket ticket) {
//        Show show = showValidator.validateExists(showId);
//        show.getTickets().add(ticket);
//        showRepository.save(show);
//    }
//
//    public void removeTicket(long showId, Ticket ticket) {
//        Show show = showValidator.validateExists(showId);
//        show.getTickets().remove(ticket);
//        showRepository.save(show);
//    }
//
//    public Set<Ticket> getAllTickets(long showId) {
//        Show show = showValidator.validateExists(showId);
//        return show.getTickets();
//    }
//
//    public Set<Seat> getAllSeats(long showId) {
//        Show show = showValidator.validateExists(showId);
//        return show.getVenue().getSeats();
//    }
//
//    @Transactional(readOnly = true)
//    public ShowOutputDto getShowWithVenueDetails(long showId) {
//        Show show = showValidator.validateExists(showId);
//        show.setEndDateTime(calculateEndDateTime(show));
//        return mapperService.mapToShowDto(show);
//    }

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
        //TODO - stworzyć contentValidator
        Content content = movieValidator.validateExists(showFromDto.getContent().getId());
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
        System.out.println(show.getEndDateTime());
    }

}
