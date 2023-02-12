package com.cinema.cinema.utils;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.content.model.MovieInputDto;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreOutputDto;
import com.cinema.cinema.themes.genre.model.GenreInputDto;
import com.cinema.cinema.themes.seat.model.*;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberOutputDto;
import com.cinema.cinema.themes.subscriber.model.SubscriberInputDto;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeOutputDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeInputDto;
import com.cinema.cinema.themes.user.model.*;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.themes.venue.model.VenueInputDto;
import com.cinema.cinema.themes.venue.model.VenueOutputDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class DtoMapperService {

    private final ModelMapper mapper;

    public AgeRestriction mapToAgeRestriction(AgeRestrictionInputDto ageRestrictionDto) {
        return mapper.map(ageRestrictionDto, AgeRestriction.class);
    }

    public AgeRestrictionOutputDto mapToAgeRestrictionDto(AgeRestriction ageRestriction) {
        return mapper.map(ageRestriction, AgeRestrictionOutputDto.class);
    }

    public Genre mapToGenre(GenreInputDto genreDto) {
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

    public TicketTypeOutputDto mapToTicketTypeDto(TicketType ticketType) {
        return mapper.map(ticketType, TicketTypeOutputDto.class);
    }

    public Movie mapToMovie(MovieInputDto movieDto) {
        return mapper.map(movieDto, Movie.class);
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
        venueDto.setRowsNumber(venue.getRowsNumber());
        venueDto.setColumnsNumber(venue.getColumnsNumber());
        setVenueOutputDtoSeats(venueDto, venue);
        return venueDto;
    }

    private void setVenueOutputDtoSeats(VenueOutputDto venueDto, Venue venue) {
        Set<SingleSeatOutputDto> vipSingleSeats = new HashSet<>();
        Set<DoubleSeatOutputDto> doubleSeats = new HashSet<>();
        for (Seat seat : venue.getSeats()) {
            if (seat instanceof SingleSeat) {
                if (seat.isVip() && !((SingleSeat) seat).isPartOfCombinedSeat()) {
                    vipSingleSeats.add(mapToSingleSeatDto((SingleSeat) seat));
                }
            } else if (seat instanceof DoubleSeat) {
                doubleSeats.add(mapToDoubleSeatDto((DoubleSeat) seat));
            }

        }
        venueDto.setVipSeats(vipSingleSeats);
        venueDto.setDoubleSeats(doubleSeats);
    }

    public SingleSeatOutputDto mapToSingleSeatDto(SingleSeat seat) {
        return mapper.map(seat, SingleSeatOutputDto.class);
    }

    public DoubleSeatOutputDto mapToDoubleSeatDto(DoubleSeat doubleSeat) {
        DoubleSeatOutputDto doubleSeatDto = new DoubleSeatOutputDto();
        doubleSeatDto.setId(doubleSeat.getId());
        doubleSeatDto.setVip(doubleSeat.isVip());
        doubleSeatDto.setLeft(mapToSingleSeatDto(doubleSeat.getLeft()));
        doubleSeatDto.setRight(mapToSingleSeatDto(doubleSeat.getRight()));
        return doubleSeatDto;
    }

}
