package com.cinema.cinema.utils;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieDtoRead;
import com.cinema.cinema.themes.content.model.MovieDtoWrite;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreDtoRead;
import com.cinema.cinema.themes.genre.model.GenreDtoWrite;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoRead;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoWrite;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoRead;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
import com.cinema.cinema.themes.user.model.*;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.themes.venue.model.VenueDtoWrite;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DtoMapperService {

    private final ModelMapper mapper;

    public AgeRestriction mapToAgeRestriction(AgeRestrictionDtoWrite ageRestrictionDto) {
        return mapper.map(ageRestrictionDto, AgeRestriction.class);
    }

    public AgeRestrictionDtoRead mapToAgeRestrictionDto(AgeRestriction ageRestriction) {
        return mapper.map(ageRestriction, AgeRestrictionDtoRead.class);
    }

    public Genre mapToGenre(GenreDtoWrite genreDto) {
        return mapper.map(genreDto, Genre.class);
    }

    public GenreDtoRead mapToGenreDto(Genre genre) {
        return mapper.map(genre, GenreDtoRead.class);
    }

    public Subscriber mapToSubscriber(SubscriberDtoWrite subscriberDto) {
        return mapper.map(subscriberDto, Subscriber.class);
    }

    public SubscriberDtoRead mapToSubscriberDto(Subscriber subscriber) {
        return mapper.map(subscriber, SubscriberDtoRead.class);
    }

    public TicketType mapToTicketType(TicketTypeDtoWrite ticketTypeDto) {
        return mapper.map(ticketTypeDto, TicketType.class);
    }

    public TicketTypeDtoRead mapToTicketTypeDto(TicketType ticketType) {
        return mapper.map(ticketType, TicketTypeDtoRead.class);
    }

    public Movie mapToMovie(MovieDtoWrite movieDto) {
        return mapper.map(movieDto, Movie.class);
    }

    public MovieDtoRead mapToMovieDto(Movie movie) {
        return mapper.map(movie, MovieDtoRead.class);
    }

    public AdminUser mapToAdminUser(AdminUserDtoWrite adminDto) {
        return mapper.map(adminDto, AdminUser.class);
    }

    public AdminUserDtoRead mapToAdminUserDto(AdminUser admin) {
        return mapper.map(admin, AdminUserDtoRead.class);
    }

    public StandardUser mapToStandardUser(StandardUserDtoWrite userDto) {
        return mapper.map(userDto, StandardUser.class);
    }

    public StandardUserDtoRead mapToStandardUserDto(StandardUser user) {
        return mapper.map(user, StandardUserDtoRead.class);
    }

    public Venue mapToVenue(VenueDtoWrite venueDto) {
        Venue venue = new Venue();
        venue.setName(venueDto.getName());
        venue.setRowsNumber(venueDto.getRowsNumber());
        venue.setColumnsNumber(venueDto.getColumnsNumber());
        return venue;
    }

//    public VenueDtoRead mapToVenueDto(Venue venue) {
//        return mapper.map(venue, VenueDtoRead.class);
//    }

//    public SingleSeat mapToSingleSeat(SeatDtoWrite seatDto) {
//        return mapper.map(seatDto, SingleSeat.class);
//    }

}
