package com.cinema.cinema.utils;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionIdDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.content.model.*;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreIdDto;
import com.cinema.cinema.themes.genre.model.GenreOutputDto;
import com.cinema.cinema.themes.genre.model.GenreInputDto;
import com.cinema.cinema.themes.seat.model.*;
import com.cinema.cinema.themes.show.model.*;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberOutputDto;
import com.cinema.cinema.themes.subscriber.model.SubscriberInputDto;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.ticket.model.TicketInputDto;
import com.cinema.cinema.themes.ticket.model.TicketOutputDto;
import com.cinema.cinema.themes.ticket.model.TicketShortDto;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeIdDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeOutputDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeInputDto;
import com.cinema.cinema.themes.user.model.*;
import com.cinema.cinema.themes.venue.model.*;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class DtoMapperService {

    private final ModelMapper mapper;

    public AgeRestriction mapToAgeRestriction(AgeRestrictionInputDto ageRestrictionDto) {
        return mapper.map(ageRestrictionDto, AgeRestriction.class);
    }

    private AgeRestriction mapToAgeRestriction(AgeRestrictionIdDto ageRestrictionDto) {
        return mapper.map(ageRestrictionDto, AgeRestriction.class);
    }

    public AgeRestrictionOutputDto mapToAgeRestrictionDto(AgeRestriction ageRestriction) {
        return mapper.map(ageRestriction, AgeRestrictionOutputDto.class);
    }

    public Genre mapToGenre(GenreInputDto genreDto) {
        return mapper.map(genreDto, Genre.class);
    }

    private Genre mapToGenre(GenreIdDto genreDto) {
        return mapper.map(genreDto, Genre.class);
    }

    public GenreOutputDto mapToGenreDto(Genre genre) {
        return mapper.map(genre, GenreOutputDto.class);
    }

    public Subscriber mapToSubscriber(SubscriberInputDto subscriberDto) {
        return mapper.map(subscriberDto, Subscriber.class);
    }

    public SubscriberOutputDto mapToSubscriberDto(Subscriber subscriber) {
        return mapper.map(subscriber, SubscriberOutputDto.class);
    }

    public TicketType mapToTicketType(TicketTypeInputDto ticketTypeDto) {
        return mapper.map(ticketTypeDto, TicketType.class);
    }

    private TicketType mapToTicketType(TicketTypeIdDto ticketTypeDto) {
        return mapper.map(ticketTypeDto, TicketType.class);

    }

    public TicketTypeOutputDto mapToTicketTypeDto(TicketType ticketType) {
        return mapper.map(ticketType, TicketTypeOutputDto.class);
    }

    public Movie mapToMovie(MovieInputDto movieDto) {
        return mapper.map(movieDto, Movie.class);

    }

    private Content mapToMovie(ContentIdDto contentDto) {
        return mapper.map(contentDto, Movie.class);
    }

    public MovieOutputDto mapToMovieDto(Movie movie) {
        return mapper.map(movie, MovieOutputDto.class);
    }

    public AdminUser mapToAdminUser(AdminUserInputDto adminDto) {
        return mapper.map(adminDto, AdminUser.class);
    }

    public AdminUserOutputDto mapToAdminUserDto(AdminUser admin) {
        return mapper.map(admin, AdminUserOutputDto.class);
    }

    public StandardUser mapToStandardUser(StandardUserInputDto userDto) {
        return mapper.map(userDto, StandardUser.class);
    }

    public StandardUserOutputDto mapToStandardUserDto(StandardUser user) {
        return mapper.map(user, StandardUserOutputDto.class);
    }

    private Venue mapToVenue(VenueIdDto venueDto) {
        return mapper.map(venueDto, Venue.class);
    }

    private VenueShortDto mapToVenueShortDto(Venue venue) {
        return mapper.map(venue, VenueShortDto.class);
    }

    public Venue mapToVenue(VenueInputDto venueDto) {
        Venue venue = new Venue();
        venue.setName(venueDto.getName());
        venue.setRowsNumber(venueDto.getRowsNumber());
        venue.setColumnsNumber(venueDto.getColumnsNumber());
        return venue;
    }

    public VenueOutputDto mapToVenueDto(Venue venue) {
        VenueOutputDto venueDto = new VenueOutputDto();
        venueDto.setId(venue.getId());
        setVenueOutputDtoSeats(venueDto, venue);
        return venueDto;
    }

    private void setVenueOutputDtoSeats(VenueOutputDto venueDto, Venue venue) {
        Set<SingleSeatOutputDto> singleSeats = new HashSet<>();
        Set<DoubleSeatOutputDto> doubleSeats = new HashSet<>();
        for (Seat seat : venue.getSeats()) {
            if (seat instanceof SingleSeat) {
                if (!((SingleSeat) seat).isPartOfCombinedSeat()) {
                    singleSeats.add(mapToSingleSeatDto((SingleSeat) seat));
                }
            } else if (seat instanceof DoubleSeat) {
                doubleSeats.add(mapToDoubleSeatDto((DoubleSeat) seat));
            }
        }
        venueDto.setSingleSeats(singleSeats);
        venueDto.setDoubleSeats(doubleSeats);
    }

    private Seat mapToSeat(SeatIdDto seatDto) {
        return mapper.map(seatDto, SingleSeat.class);
    }

    private SingleSeatOutputDto mapToSingleSeatDto(SingleSeat seat) {
        return mapper.map(seat, SingleSeatOutputDto.class);
    }

    private DoubleSeatOutputDto mapToDoubleSeatDto(DoubleSeat doubleSeat) {
        return mapper.map(doubleSeat, DoubleSeatOutputDto.class);

    }

    private Show mapToShow(ShowIdDto showDto) {
        return mapper.map(showDto, Show.class);
    }

    public Show mapToShow(ShowInputDto showDto) {
        Show show = new Show();
        show.setStartDateTime(showDto.getStartDateTime());
        show.setBreakAfterInMinutes(showDto.getBreakAfterInMinutes());
        if (showDto.getVenue() != null) {
            show.setVenue(mapToVenue(showDto.getVenue()));
        }
        if (showDto.getContent() != null) {
            show.setContent(mapToMovie(showDto.getContent()));
        }
        return show;
    }

    public ShowOutputDto mapToShowDto(Show show) {
        ShowOutputDto showDto = new ShowOutputDto();
        showDto.setId(show.getId());
        showDto.setStartDateTime(show.getStartDateTime());
        showDto.setBreakAfterInMinutes(show.getBreakAfterInMinutes());
        showDto.setVenue(mapToVenueDto(show.getVenue()));
        if (show.getContent() instanceof Movie) {
            showDto.setMovieContent(mapToMovieDto((Movie) show.getContent()));
        }
        Set<Ticket> tickets = show.getTickets();
        List<TicketShortDto> ticketsDto = tickets.stream().map(this::mapToTicketShortDto).toList();
        showDto.setTickets(ticketsDto);
        return showDto;
    }

    public ShowShortDto mapToShowShortDto(Show show) {
        ShowShortDto showDto = new ShowShortDto();
        showDto.setId(show.getId());
        showDto.setStartDateTime(show.getStartDateTime());
        showDto.setVenue(mapToVenueShortDto(show.getVenue()));
        if (show.getContent() instanceof Movie) {
            showDto.setMovieContent(mapToMovieDto((Movie) show.getContent()));
        }
        return showDto;
    }

    public Ticket mapToTicket(TicketInputDto ticketDto) {
        Ticket ticket = new Ticket();
        if (ticketDto.getShow() != null) {
            ticket.setShow(mapToShow(ticketDto.getShow()));
        }
        if (ticketDto.getSeat() != null) {
            ticket.setSeat(mapToSeat(ticketDto.getSeat()));
        }
        if (ticketDto.getTicketType() != null) {
            ticket.setType(mapToTicketType(ticketDto.getTicketType()));
        }
        return ticket;
    }

    public TicketOutputDto mapToTicketDto(Ticket ticket) {
        TicketOutputDto ticketDto = new TicketOutputDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setPaid(ticket.isPaid());
        ticketDto.setShow(mapToShowShortDto(ticket.getShow()));
        ticketDto.setTicketType(mapToTicketTypeDto(ticket.getType()));
        if (ticket.getSeat() instanceof SingleSeat) {
            ticketDto.setSingleSeat(mapToSingleSeatDto((SingleSeat) ticket.getSeat()));
        } else if (ticket.getSeat() instanceof DoubleSeat) {
            ticketDto.setDoubleSeat(mapToDoubleSeatDto((DoubleSeat) ticket.getSeat()));
        }
        return ticketDto;
    }

    public TicketShortDto mapToTicketShortDto(Ticket ticket) {
        TicketShortDto ticketDto = new TicketShortDto();
        ticketDto.setId(ticket.getId());
        ticketDto.setPaid(ticket.isPaid());
        ticketDto.setTicketType(mapToTicketTypeDto(ticket.getType()));
        if (ticket.getSeat() instanceof SingleSeat) {
            ticketDto.setSingleSeat(mapToSingleSeatDto((SingleSeat) ticket.getSeat()));
        } else if (ticket.getSeat() instanceof DoubleSeat) {
            ticketDto.setDoubleSeat(mapToDoubleSeatDto((DoubleSeat) ticket.getSeat()));
        }
        return ticketDto;
    }

}
