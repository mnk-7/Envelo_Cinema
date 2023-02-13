package com.cinema.cinema.themes.show.model;

import com.cinema.cinema.themes.content.model.Content;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.venue.model.Venue;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @NotNull(message = "Field is mandatory")
    @Future(message = "Field needs to be in the future")
    private LocalDateTime startDateTime;

    @Transient
    private LocalDateTime endDateTime;

    @NotNull(message = "Field is mandatory")
    @Min(value = 15, message = "Value cannot be less than {value}")
    private Integer breakAfterInMinutes;

    @OneToMany(mappedBy = "show")
    private Set<Ticket> tickets = new HashSet<>();

}
