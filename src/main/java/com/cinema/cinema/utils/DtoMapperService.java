package com.cinema.cinema.utils;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreDtoRead;
import com.cinema.cinema.themes.genre.model.GenreDtoWrite;
import com.cinema.cinema.themes.subscriber.model.Subscriber;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoRead;
import com.cinema.cinema.themes.subscriber.model.SubscriberDtoWrite;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoRead;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
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

}
