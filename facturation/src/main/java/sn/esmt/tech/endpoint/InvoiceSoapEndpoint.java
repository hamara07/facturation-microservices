package sn.esmt.tech.endpoint;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import sn.esmt.tech.entities.Invoice;
import sn.esmt.tech.service.InvoiceService;

import sn.esmt.tech.ws.GetInvoiceResponse;
import sn.esmt.tech.ws.GetInvoiceRequest;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.GregorianCalendar;

@Endpoint
public class InvoiceSoapEndpoint {

    private static final String NAMESPACE_URI = "http://sn.esmt.tech/invoice";

    @Autowired
    private InvoiceService invoiceService;

    private XMLGregorianCalendar toXmlGregorian(LocalDate date) {
        if (date == null) return null;

        try {
            GregorianCalendar gc = GregorianCalendar.from(
                date.atStartOfDay(java.time.ZoneId.systemDefault())
            );
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        } catch (Exception e) {
            throw new RuntimeException("Erreur conversion date SOAP", e);
        }
    }

    @PayloadRoot(
        namespace = NAMESPACE_URI,
        localPart = "getInvoiceRequest"
    )
    @ResponsePayload
    public GetInvoiceResponse getInvoice(
        @RequestPayload GetInvoiceRequest request) {

        // 1️ Appel du service métier existant
        Invoice invoice = invoiceService.getInvoice(request.getId());

        // 2️ Mapping Entity → SOAP Response
        GetInvoiceResponse response = new GetInvoiceResponse();
        response.setId(invoice.getId());
        response.setClientName(
            invoice.getClient().getNom() + " " + invoice.getClient().getPrenom()
        );
        response.setDescription(invoice.getDescription());
        response.setAmount(invoice.getAmount());
        response.setStatus(invoice.getStatus());
        response.setDateEmission(toXmlGregorian(invoice.getDateEmission()));

        if (invoice.getDatePaiement() != null) {
            response.setDatePaiement(toXmlGregorian(invoice.getDatePaiement()));
        }

        return response;
    }
}
