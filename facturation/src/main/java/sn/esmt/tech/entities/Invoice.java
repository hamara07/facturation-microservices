package sn.esmt.tech.entities;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId") // colonne qui référence Client
    private Client client;

    private BigDecimal amount;

    private String description;

    private LocalDate dateEmission;

    private LocalDate datePaiement;

    private String status; // "PENDING", "PAID", "CANCELLED"

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod; // "CARD", "TRANSFER", "CASH"

    public Invoice() {
        this.status = "PENDING";
        this.dateEmission = LocalDate.now();
    }

//    public Invoice(Client client, BigDecimal amount, String description,
//                   LocalDate dateEmission, LocalDate datePaiement,
//                   String status, PaymentMethod paymentMethod) {
//        this.client = client;
//        this.amount = amount;
//        this.description = description;
//        this.dateEmission = dateEmission;
//        this.datePaiement = datePaiement;
//        this.status = status;
//        this.paymentMethod = paymentMethod;
//    }

    public Invoice(Client client, BigDecimal amount, String description) {
        this();
        this.client = client;
        this.amount = amount;
        this.description = description;
    }

    public Invoice(Client client, BigDecimal amount, String description,
                   LocalDate dateEmission, PaymentMethod paymentMethod) {
        this.client = client;
        this.amount = amount;
        this.description = description;
        this.dateEmission = dateEmission;
        this.paymentMethod = paymentMethod;
        this.status = "PENDING";        // par défaut
    }

    // Getters et Setters


    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public enum PaymentMethod {
        CARD, TRANSFER, CASH
    }
}

