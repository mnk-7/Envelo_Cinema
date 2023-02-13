package com.cinema.cinema.themes.show.model;

import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.venue.model.VenueShortOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowOutputShortDto {

    private Long id;
    private VenueShortOutputDto venue;
    private MovieOutputDto movieContent;
    private LocalDateTime startDateTime;

}
