package com.cinema.cinema.themes.ticket.model;

import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.show.model.Show;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    private boolean isPaid;

}
