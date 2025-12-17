package sn.esmt.tech;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sn.esmt.tech.entities.Client;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.repository.ClientRepository;
import sn.esmt.tech.repository.InvoiceRepository;
import sn.esmt.tech.service.InvoiceService;
import sn.esmt.tech.service.dto.InvoicePaymentRequest;
import sn.esmt.tech.service.dto.InvoiceRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class InvoicesTestService {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private InvoiceService invoiceService;




// Test pour la creation
    @Test
    void createInvoiceTest() {
        Client client = new Client();
        client.setId(1L);

        InvoiceRequest req = new InvoiceRequest(
            1L,
            new BigDecimal("1000"),
            "Facture test",
            LocalDate.now(),
            Invoice.PaymentMethod.CASH

        );
        Invoice invoice = new Invoice();
        invoice.setClient(client);

        when(clientRepository.findById(1L))
            .thenReturn(Optional.of(client));

        when(invoiceRepository.save(any(Invoice.class)))
            .thenReturn(invoice);

        Invoice result = invoiceService.createInvoice(req);

        assertNotNull(result);
        assertEquals(client, result.getClient());
    }

    // Test pour Liste des factures d'un client
    @Test
    void getInvoicesByClientTest() {
        Invoice inv1 = new Invoice();
        Invoice inv2 = new Invoice();

        when(invoiceRepository.findByClientId(2L))
            .thenReturn(List.of(inv1, inv2));

        List<Invoice> invoices = invoiceService.getInvoicesByClient(2L);

        assertEquals(2, invoices.size());
    }

    //Test pour la consultation details
    @Test
    void getInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setId(2L);

        when(invoiceRepository.findById(2L))
            .thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.getInvoice(2L);

        assertEquals(2L, result.getId());
    }

    // test pour le paiement facture
    @Test
    void payInvoiceTest() {
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        InvoicePaymentRequest req = new InvoicePaymentRequest(
            1L,
            LocalDate.now(),
            "PAID",
            Invoice.PaymentMethod.CASH
        );

        when(invoiceRepository.findById(1L))
            .thenReturn(Optional.of(invoice));

        when(invoiceRepository.save(any(Invoice.class)))
            .thenReturn(invoice);   //

        Invoice paidInvoice = invoiceService.payInvoice(req);

        assertEquals("PAID", paidInvoice.getStatus());
        assertEquals(Invoice.PaymentMethod.CASH, paidInvoice.getPaymentMethod());
    }

    // test Total des factures impayées pour un client
    @Test
    void totalUnpaidInvoicesTest() {
        when(invoiceRepository.getTotalAmountByClient(1L))
            .thenReturn(new BigDecimal("5000"));

        BigDecimal total = invoiceService.getTotalUnpaidInvoices(1L);

        assertEquals(new BigDecimal("5000"), total);
    }
}
