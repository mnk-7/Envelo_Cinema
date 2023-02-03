package com.cinema.cinema.themes.ticket;

import com.cinema.cinema.themes.ticketType.TicketType;
import com.cinema.cinema.themes.order.Order;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.show.Show;
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

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "ticket_type_id")
    private TicketType type;

    private boolean isPaid;

}
