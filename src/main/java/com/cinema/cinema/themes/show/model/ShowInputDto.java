package com.cinema.cinema.themes.show.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowInputDto {

    private Long venueId;
    private Long movieId; //dla każdego innego typu content potrzebne osobne pole
    private Integer breakAfterInMinutes;
    private LocalDateTime startDateTime;

}
