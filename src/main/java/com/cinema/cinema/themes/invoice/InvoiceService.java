package com.cinema.cinema.themes.invoice;

import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.order.OrderService;
import com.cinema.cinema.themes.order.OrderValidator;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final OrderService orderService;
    private final InvoiceValidator invoiceValidator;
    private final OrderValidator orderValidator;
    private final StandardUserValidator userValidator;

    @Transactional(readOnly = true)
    public Invoice getInvoice(long invoiceId) {
        return invoiceValidator.validateExists(invoiceId);
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Invoice> getAllInvoices(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        return user.getOrders().stream().map(Order::getInvoice).toList();
    }

    @Transactional
    public Invoice generateInvoice(Invoice invoice) {
        Order order = orderValidator.validateExists(invoice.getOrder().getId());
        invoiceValidator.validateNotGenerated(order);
        invoice.setOrder(order);
        invoice.setGenerationDateTime(LocalDateTime.now());
        invoice.setNumber(InvoiceNumberGenerator.generateNumber());
        invoiceValidator.validateInput(invoice);
        invoiceRepository.save(invoice);
        orderService.addInvoice(order, invoice);
        return invoice;
    }

    //TODO
//    @Transactional
//    public void removeOrder(Invoice invoice) {
//        invoice.setOrder(null);
//        invoiceRepository.save(invoice);
//    }

}
