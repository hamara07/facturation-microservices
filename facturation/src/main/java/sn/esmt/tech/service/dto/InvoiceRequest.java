package sn.esmt.tech.service.dto;

import sn.esmt.tech.entities.Client;
import sn.esmt.tech.entities.Invoice;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceRequest(Long clientId, BigDecimal amount,
                             String description, LocalDate dateEmission, Invoice.PaymentMethod paymentMethod) {

}

