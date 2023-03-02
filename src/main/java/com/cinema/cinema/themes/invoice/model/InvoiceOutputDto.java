package com.cinema.cinema.themes.invoice.model;

import com.cinema.cinema.themes.order.model.OrderOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceOutputDto {

    private Long id;
    private OrderOutputDto order;
    private String number;
    private LocalDateTime generationDateTime;
    private String recipientName;
    private String recipientAddress;
    private String recipientNip;

}
