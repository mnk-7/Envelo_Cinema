package com.cinema.cinema.themes.invoice.model;

import com.cinema.cinema.themes.order.model.OrderIdDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceInputDto {

    private OrderIdDto order;
    private String recipientName;
    private String recipientAddress;
    private String recipientNip;

}
