package sn.esmt.tech.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.esmt.tech.entities.Client;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.repository.ClientRepository;
import sn.esmt.tech.repository.InvoiceRepository;
import sn.esmt.tech.service.dto.InvoicePaymentRequest;
import sn.esmt.tech.service.dto.InvoiceRequest;
import sn.esmt.tech.service.dto.TotalAmountResponse;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientRepository ClientRepository;


    // 1. Création
    public Invoice createInvoice(InvoiceRequest req){
        Client client = ClientRepository.findById(req.clientId())
            .orElseThrow(() -> new RuntimeException("Client not found"));

        Invoice invoice = new Invoice(
                client,
                req.amount(),
                req.description(),
                req.dateEmission(),
                req.paymentMethod()

        );
        return invoiceRepository.save(invoice);
    }

    // 2. Liste des factures d'un client
    public List<Invoice> getInvoicesByClient(Long clientId) {
        return invoiceRepository.findByClientId(clientId);
    }

    // Affiche toutes les factures
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    // 3. Consultation
    public Invoice getInvoice(Long id) {
        return invoiceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Facture introuvable"));
    }

    // 4. Paiement facture
    public Invoice payInvoice(InvoicePaymentRequest req) {
        Invoice invoice = getInvoice(req.invoiceId());

        invoice.setStatus("PAID");
        invoice.setPaymentMethod(req.paymentMethod());
        invoice.setDatePaiement(req.datePaiement());

        return invoiceRepository.save(invoice);
    }

    // 5. Total des factures impayées pour un client
    public BigDecimal getTotalUnpaidInvoices(Long clientId) {
        BigDecimal total = invoiceRepository.getTotalAmountByClient(clientId);

        return total != null ? total : BigDecimal.ZERO;
    }


}
