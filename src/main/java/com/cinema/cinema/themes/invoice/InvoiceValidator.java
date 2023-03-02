package com.cinema.cinema.themes.invoice;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceValidator extends ValidatorService<Invoice> {

    private final InvoiceRepository invoiceRepository;

    public InvoiceValidator(Validator validator, InvoiceRepository invoiceRepository) {
        super(validator);
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice validateExists(long invoiceId) {
        Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
        if (invoice.isEmpty()) {
            throw new ElementNotFoundException("Invoice with ID " + invoiceId + " not found");
        }
        return invoice.get();
    }

    public void validateNotGenerated(Order order) {
        if (order.getInvoice() != null) {
            throw new ProcessingException("Invoice has already been generated");
        }
    }

}
