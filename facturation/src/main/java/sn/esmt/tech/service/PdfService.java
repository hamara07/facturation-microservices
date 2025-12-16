package sn.esmt.tech.service;


import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import sn.esmt.tech.entities.Invoice;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {

    public ByteArrayInputStream generateInvoicePdf(Invoice invoice) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

            // Titre
            Paragraph title = new Paragraph("Facture #" + invoice.getId(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Client
            Paragraph client = new Paragraph(
                "Client : " + invoice.getClient().getNom() + " " + invoice.getClient().getPrenom(), normalFont
            );
            client.setSpacingAfter(10);
            document.add(client);

            // Description
            Paragraph description = new Paragraph("Description : " + invoice.getDescription(), normalFont);
            description.setSpacingAfter(10);
            document.add(description);

            // Montant
            Paragraph amount = new Paragraph("Montant : " + invoice.getAmount(), normalFont);
            amount.setSpacingAfter(10);
            document.add(amount);

            // Status
            Paragraph status = new Paragraph("Status : " + invoice.getStatus(), normalFont);
            status.setSpacingAfter(10);
            document.add(status);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            // Date émission
            Paragraph dateEmission = new Paragraph(
                "Date émission : " + invoice.getDateEmission().format(formatter),
                normalFont
            );
            dateEmission.setSpacingAfter(10);
            document.add(dateEmission);

            // Date paiement (si payé)
            if (invoice.getDatePaiement() != null) {
                Paragraph datePaiement = new Paragraph(
                    "Date paiement : " + invoice.getDatePaiement().format(formatter),
                    normalFont
                );
                datePaiement.setSpacingAfter(10);
                document.add(datePaiement);
            }

            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

}
