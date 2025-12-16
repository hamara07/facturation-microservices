package sn.esmt.tech.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.service.InvoiceService;
import sn.esmt.tech.service.dto.InvoicePaymentRequest;
import sn.esmt.tech.service.dto.InvoiceRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

//@Controller
@RestController
@RequestMapping("/api/rest")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;


    //Crée une nouvelle
    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody InvoiceRequest req){

        return invoiceService.createInvoice(req);
    }

    //Récupère  les détails d’une facture
    @GetMapping("/invoices/{id}")
    public  Invoice getInvoice(@PathVariable Long id){

        return invoiceService.getInvoice(id);
    }


    //Récupère toutes les factures d’un client
    @GetMapping("/clients/{clientId}/invoices")
    public List<Invoice> getInvoicesByClient(@PathVariable Long clientId){
        return invoiceService.getInvoicesByClient(clientId);
    }


    //Met à jour le statut de paiement d’une facture
    @PutMapping("/invoices/{id}/pay")
    public Invoice payInvoice(
        @PathVariable Long id,
        @RequestBody InvoicePaymentRequest req) {

        // On met l'id de l'URL dans le DTO
        req = new InvoicePaymentRequest(
            id,
            req.datePaiement(),
            req.status(),
            req.paymentMethod()
        );

        return invoiceService.payInvoice(req);
    }

    //Calcule le montant total des factures impayées pour un client donné
    @GetMapping("/clients/{clientId}/total")
    public Map<String, BigDecimal> getTotalForClient(@PathVariable Long clientId) {
        BigDecimal total = invoiceService.getTotalUnpaidInvoices(clientId);
        return Map.of("totalAmount", total);
    }




}
