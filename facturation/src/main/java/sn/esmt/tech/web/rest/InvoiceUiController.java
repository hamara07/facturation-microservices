package sn.esmt.tech.web.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.service.InvoiceService;
import sn.esmt.tech.service.PdfService;
import sn.esmt.tech.service.dto.InvoicePaymentRequest;
import sn.esmt.tech.service.dto.InvoiceRequest;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/api/invoices")
public class InvoiceUiController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PdfService pdfService;

    // 1️ Liste de toutes les factures
    @GetMapping
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.getAllInvoices());
        return "invoices/list";
    }

    // 2️ Formulaire de création
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute(
            "invoiceRequest",
            new InvoiceRequest(null, null, null, null, null)
        );
        return "invoices/create";
    }

    // 3️ Création facture
    @PostMapping("/create")
    public String createInvoice(@ModelAttribute InvoiceRequest req) {
        invoiceService.createInvoice(req);
        return "redirect:/api/invoices";
    }

    // 4️ Factures d’un client
    @GetMapping("/client/{clientId}")
    public String getInvoicesByClient(@PathVariable Long clientId, Model model) {
        model.addAttribute("invoices", invoiceService.getInvoicesByClient(clientId));
        model.addAttribute("clientId", clientId);
        return "invoices/client-list";
    }

    // 5⃣ Total des impayés d’un client
    @GetMapping("/client/{clientId}/total")
    public String getClientTotal(@PathVariable Long clientId, Model model) {
        model.addAttribute("total", invoiceService.getTotalUnpaidInvoices(clientId));
        model.addAttribute("clientId", clientId);
        return "invoices/total";
    }

    // 6️ Détail d’une facture
    @GetMapping("/{id}")
    public String getInvoice(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.getInvoice(id));
        return "invoices/details";
    }

    // 7️ Formulaire de paiement
    @GetMapping("/{id}/pay")
    public String showPayForm(@PathVariable Long id, Model model) {
        model.addAttribute("invoice", invoiceService.getInvoice(id));
        model.addAttribute(
            "paymentRequest",
            new InvoicePaymentRequest(id, null, null, null)
        );
        return "invoices/pay";
    }

    // Génération PDF
    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoice(id);
        ByteArrayInputStream bis = pdfService.generateInvoicePdf(invoice);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=facture_" + id + ".pdf");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(bis.readAllBytes());
    }

    // 8 Paiement
    @PostMapping("/{id}/pay")
    public String payInvoice(@PathVariable Long id, @ModelAttribute InvoicePaymentRequest req) {
        invoiceService.payInvoice(
            new InvoicePaymentRequest(id, req.datePaiement(), req.status(), req.paymentMethod())
        );
        return "redirect:/api/invoices/" + id;
    }
}





