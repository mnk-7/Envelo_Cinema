package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.content.model.Content;
import com.cinema.cinema.themes.content.validator.ContentValidator;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.show.model.ShowInputDto;
import com.cinema.cinema.themes.show.model.ShowOutputDto;
import com.cinema.cinema.themes.show.model.ShowOutputShortDto;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.ticket.model.TicketOutputShortDto;
import com.cinema.cinema.themes.venue.VenueValidator;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.utils.DtoMapperService;
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
                .map(mapperService::mapToShowShortDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ShowOutputShortDto> getAllShowsForCurrentWeek() {
        LocalDateTime dateTimeFrom = LocalDateTime.now();
        LocalDateTime dateTimeTo = setDateTimeTo(dateTimeFrom);
        List<Show> shows = showRepository.findAllByStartDateTimeBetween(dateTimeFrom, dateTimeTo);
        return shows.stream()
                .map(mapperService::mapToShowShortDto)
                .toList();
    }

    @Transactional
    public ShowOutputShortDto addShow(ShowInputDto showDto) {
        Show showFromDto = mapperService.mapToShow(showDto);
        showValidator.validateInput(showFromDto);
        Show show = createShow(showFromDto);
        showValidator.validateVenueAndDateTime(show);
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
        //działa bez nullowania venue i content;
        //delete all tickets nie jest konieczne z uwagi na walidację, że show można usunąć jedynie, jeśli nie ma żadnego ticketu
        showRepository.deleteById(showId);
    }

    @Transactional
    public void addTicket(Show show, Ticket ticket) {
        show.getTickets().add(ticket);
        showValidator.validateInput(show);
        showRepository.save(show);
    }

    @Transactional
    public void removeTicket(Show show, Ticket ticket) {
        show.getTickets().remove(ticket);
        showValidator.validateInput(show);
        showRepository.save(show);
    }

    @Transactional(readOnly = true)
    public List<TicketOutputShortDto> getAllTickets(long showId) {
        List<Ticket> tickets = getAllTicketsNotDto(showId);
        return tickets.stream()
                .map(mapperService::mapToTicketShortDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Ticket> getAllTicketsNotDto(long showId) {
        Show show = showValidator.validateExists(showId);
        return show.getTickets().stream().toList();
    }

    @Transactional(readOnly = true)
    public ShowOutputDto getShowWithVenueDetails(long showId) {
        Show show = showValidator.validateExists(showId);
        return mapperService.mapToShowDto(show);
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
