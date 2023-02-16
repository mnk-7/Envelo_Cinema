package com.cinema.cinema.themes.show.model;

import com.cinema.cinema.themes.content.model.ContentIdDto;
import com.cinema.cinema.themes.venue.model.VenueIdDto;
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

    private VenueIdDto venue;
    private ContentIdDto content;
    private Integer breakAfterInMinutes;
    private LocalDateTime startDateTime;

}
