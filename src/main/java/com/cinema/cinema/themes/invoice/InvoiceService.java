package com.cinema.cinema.themes.invoice;

import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.order.OrderService;
import com.cinema.cinema.themes.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;

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
        //orderService.addInvoice(order.getId(), invoice);
        return invoice;
    }

//    public List<Invoice> getAllInvoices(long id) {
//        StandardUser user = getStandardUser(id);
//        return user.getOrders().stream().map(Order::getInvoice).toList();
//    }

}
