package com.cinema.cinema.themes.show.model;

import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.ticket.model.TicketOutputShortDto;
import com.cinema.cinema.themes.venue.model.VenueOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowOutputDto {

    private Long id;
    private VenueOutputDto venue;
    private MovieOutputDto movieContent;
    private LocalDateTime startDateTime;
    private Integer breakAfterInMinutes;
    private List<TicketOutputShortDto> tickets = new ArrayList<>();

}
