package sn.esmt.tech.service.dto;

import sn.esmt.tech.entities.Invoice;

import java.time.LocalDate;

public record InvoicePaymentRequest(
    Long invoiceId, LocalDate datePaiement, String status ,Invoice.PaymentMethod paymentMethod) { }
