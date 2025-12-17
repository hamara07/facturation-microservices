package sn.esmt.tech;


import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.service.InvoiceService;
import sn.esmt.tech.service.PdfService;
import sn.esmt.tech.web.rest.InvoiceUiController;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InvoiceUiController.class)
public class InvoicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private InvoiceService invoiceService;

    @Mock
    private PdfService pdfService;

    // Test pour LIste de toutes les facture
    @Test
    void ListInvoicesList() throws Exception {
        when(invoiceService.getAllInvoices())
            .thenReturn(List.of(new Invoice(), new Invoice()));

        mockMvc.perform(get("/api/invoices"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/list"))
            .andExpect(model().attributeExists("invoices"));
    }

    // Test pour le Formulaire de creation facture
    @Test
    void CreateFormTest() throws Exception {
        mockMvc.perform(get("/api/invoices/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/create"))
            .andExpect(model().attributeExists("invoiceRequest"));
    }

    //Test creation facture
    @Test
    void CreateInvoiceAndRedirect() throws Exception {
        mockMvc.perform(post("/api/invoices/create")
                .param("clientId", "1")
                .param("amount", "1000")
                .param("description", "Facture test")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/api/invoices"));
    }

    //Factures d’un client
    @Test
    void shouldShowInvoicesByClient() throws Exception {
        when(invoiceService.getInvoicesByClient(1L))
            .thenReturn(List.of(new Invoice()));

        mockMvc.perform(get("/api/invoices/client/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/client-list"))
            .andExpect(model().attributeExists("invoices"))
            .andExpect(model().attribute("clientId", 1L));
    }

    //Détail facture
    @Test
    void ShowInvoiceDetails() throws Exception {
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        when(invoiceService.getInvoice(1L))
            .thenReturn(invoice);

        mockMvc.perform(get("/api/invoices/1"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/details"))
            .andExpect(model().attributeExists("invoice"));
    }

    //Formulaire de paiement
    @Test
    void ShowPayForm() throws Exception {
        when(invoiceService.getInvoice(1L))
            .thenReturn(new Invoice());

        mockMvc.perform(get("/api/invoices/1/pay"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/pay"))
            .andExpect(model().attributeExists("paymentRequest"));
    }

    //Paiement facture
    @Test
    void shouldPayInvoiceAndRedirect() throws Exception {
        mockMvc.perform(post("/api/invoices/1/pay")
                .param("status", "PAID")
            )
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/api/invoices/1"));
    }

    //Total facture
    @Test
    void shouldShowClientTotal() throws Exception {
        when(invoiceService.getTotalUnpaidInvoices(1L))
            .thenReturn(BigDecimal.valueOf(5000));

        mockMvc.perform(get("/api/invoices/client/1/total"))
            .andExpect(status().isOk())
            .andExpect(view().name("invoices/total"))
            .andExpect(model().attributeExists("total"));
    }

    //Génération PDF
    @Test
    void shouldGenerateInvoicePdf() throws Exception {
        Invoice invoice = new Invoice();
        ByteArrayInputStream bis = new ByteArrayInputStream("PDF".getBytes());

        when(invoiceService.getInvoice(1L))
            .thenReturn(invoice);

        when(pdfService.generateInvoicePdf(invoice))
            .thenReturn(bis);

        mockMvc.perform(get("/api/invoices/1/pdf"))
            .andExpect(status().isOk())
            .andExpect(header().string("Content-Type", "application/pdf"));
    }



}
