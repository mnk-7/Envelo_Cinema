package com.cinema.cinema.themes.invoice;

import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.invoice.model.InvoiceInputDto;
import com.cinema.cinema.themes.invoice.model.InvoiceOutputDto;
import com.cinema.cinema.utils.DtoMapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Invoices")
@RequestMapping("/${app.prefix}/${app.version}/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all invoices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with invoices returned"),
            @ApiResponse(responseCode = "204", description = "No invoice found")})
    public ResponseEntity<List<InvoiceOutputDto>> getAllInvoices() {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        List<InvoiceOutputDto> invoicesDto = invoices.stream()
                .map(mapperService::mapToInvoiceDto)
                .toList();
        HttpStatus status = invoicesDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(invoicesDto, status);
    }

    @GetMapping("/{invoiceId}")
    @Operation(summary = "Get invoice by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Invoice returned"),
            @ApiResponse(responseCode = "404", description = "Invoice not found")})
    public ResponseEntity<InvoiceOutputDto> getInvoice(@PathVariable long invoiceId) {
        Invoice invoice = invoiceService.getInvoice(invoiceId);
        InvoiceOutputDto invoiceDto = mapperService.mapToInvoiceDto(invoice);
        return new ResponseEntity<>(invoiceDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Generate invoice")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Invoice generated"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> generateInvoice(@RequestBody InvoiceInputDto invoiceDto) {
        Invoice invoice = mapperService.mapToInvoice(invoiceDto);
        invoice = invoiceService.generateInvoice(invoice);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{invoiceId}")
                .buildAndExpand(invoice.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

}
