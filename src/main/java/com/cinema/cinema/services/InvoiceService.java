package com.cinema.cinema.services;

import com.cinema.cinema.repositories.InvoiceRepository;
import com.cinema.cinema.utils.InvoiceNumberGenerator;
import com.cinema.cinema.exceptions.InvoiceException;
import com.cinema.cinema.models.Invoice;
import com.cinema.cinema.models.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private InvoiceRepository invoiceRepository;
    private OrderService orderService;

    public Invoice getInvoice(long id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        if (invoice.isEmpty()) {
            throw new InvoiceException("Invoice with given ID not found");
        }
        return invoice.get();
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    //method should be located in this service because it is used by user and by guest
    public Invoice generateInvoice(Order order, String recipient, String address, String nip) {
        if (order.getInvoice() != null) {
            throw new InvoiceException("Invoice has already been generated");
        }
        Invoice invoice = new Invoice();
        invoice.setNumber(InvoiceNumberGenerator.generateNumber());
        invoice.setOrder(order);
        invoice.setGenerationDateTime(LocalDateTime.now());
        invoice.setRecipientName(recipient);
        invoice.setRecipientAddress(address);
        invoice.setRecipientNip(nip);
        invoiceRepository.save(invoice);
        orderService.addInvoice(order.getId(), invoice);
        return invoice;
    }

}
