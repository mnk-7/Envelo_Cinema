package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.ticket.Ticket;
import com.cinema.cinema.themes.venue.Venue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Movie movie;
//    @JoinTable(name = "content_in_shows",
//            joinColumns = @JoinColumn(name = "show_id"),
//            inverseJoinColumns = @JoinColumn(name = "content_id"))
    //private Content content;

    private LocalDateTime startDateTime;
    private int breakAfterInMinutes;

    @OneToMany(mappedBy = "show")
    private Set<Ticket> tickets;

}
