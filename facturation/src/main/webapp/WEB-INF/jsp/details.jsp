<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><html>
<head>
  <title>Détails facture #${invoice.id}</title>
  <style>
    body { font-family: Arial; background: #f2f2f2; padding: 20px; }
    .box {
      background: white; padding: 20px; width: 450px;
      border-radius: 5px; box-shadow: 0 0 8px #ccc;
    }
    .btn { padding: 10px; display: inline-block;
      background: #007bff; color: white; text-decoration: none;
      border-radius: 4px; margin-top: 10px; }
    .btn-green { background: #28a745; }
    .btn-orange { background: #ff9800; }
  </style>
</head>

<body>

<div class="box">
  <h2>Facture #${invoice.id}</h2>

  <p><b>Client :</b> ${invoice.client.name}</p>
  <p><b>Description :</b> ${invoice.description}</p>
  <p><b>Montant :</b> ${invoice.amount}</p>
  <p><b>Status :</b> ${invoice.status}</p>
  <p><b>Date émission :</b> ${invoice.dateEmission}</p>
  <p><b>Date paiement :</b> ${invoice.datePaiement}</p>

  <br>

  <a class="btn btn-green" href="/invoice/${invoice.id}/pay">
    Payer cette facture
  </a>

  <br>

  <a class="btn btn-orange" href="/client/${invoice.client.id}">
    Voir toutes les factures du client
  </a>

  <br>

  <a class="btn" href="/invoice/all">
    Retour à toutes les factures
  </a>
</div>

</body>
</html>
