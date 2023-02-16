package com.cinema.cinema.themes.show.model;

import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.venue.model.VenueShortDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowShortDto {

    private Long id;
    private VenueShortDto venue;
    private MovieOutputDto movieContent;
    private LocalDateTime startDateTime;

}
