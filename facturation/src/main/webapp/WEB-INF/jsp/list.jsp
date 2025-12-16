<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, sn.esmt.tech.entities.Client" %><html>
<%@ page import="java.util.List, sn.esmt.tech.entities.Invoice" %><html>


<html>
<head>
  <title>Toutes les factures</title>
  <style>
    body { font-family: Arial; background: #f7f7f7; padding: 20px; }
    h2 { color: #333; }
    table { width: 100%; background: white; border-collapse: collapse; }
    th, td { border: 1px solid #ccc; padding: 10px; }
    th { background: #2d89ef; color: white; }
    tr:nth-child(even) { background: #f2f2f2; }
    .btn { padding: 8px 12px; border-radius: 4px; text-decoration: none; }
    .btn-blue { background: #007bff; color: white; }
    .btn-green { background: #28a745; color: white; }
    .btn-orange { background: #ff9800; color:white; }
    .btn-purple { background: #6f42c1; color:white; }
    .actions { display: flex; gap: 10px; }
    .create-btn { margin-bottom: 15px; display: inline-block; }
  </style>
</head>

<body>

<h2>Liste de toutes les factures</h2>

<!-- Bouton pour aller créer une nouvelle facture -->
<a href="/invoice/create" class="btn btn-green create-btn">Créer une nouvelle facture</a>

<!-- Formulaire sélection facture -->
<form method="get" id="invoiceForm">
  <label>Sélectionner une facture :</label>
  <select id="invoiceSelect" name="invoiceId">
    <c:forEach var="invoice" items="${invoices}">
      <option value="${invoice.id}" data-client="${invoice.client.id}">
        Facture ${invoice.id} - Client ${invoice.client.id}
      </option>
    </c:forEach>
  </select>

  <!-- Bouton pour voir les factures du client -->
  <button type="submit" formaction="/client" class="btn btn-orange">
    Voir factures du client
  </button>

  <!-- bouton : calcul du total facturé -->
  <button type="button" class="btn btn-purple" onclick="goToTotal()">
    Total facturé du client
  </button>
</form>

<script>
  function goToTotal() {
    const select = document.getElementById("invoiceSelect");
    const clientId = select.options[select.selectedIndex].getAttribute("data-client");
    window.location.href = "/client/" + clientId + "/total";
  }
</script>

<br><br>

<table>
  <tr>
    <th>ID</th>
    <th>Client</th>
    <th>Description</th>
    <th>Montant</th>
    <th>Status</th>
    <th>Actions</th>
  </tr>

  <c:forEach var="invoice" items="${invoices}">
    <tr>
      <td>${invoice.id}</td>
      <td>${invoice.client.name}</td>
      <td>${invoice.description}</td>
      <td>${invoice.amount}</td>
      <td>${invoice.status}</td>

      <td class="actions">
        <a class="btn btn-blue" href="/invoice/${invoice.id}">
          Voir détails
        </a>

        <a class="btn btn-orange" href="/client/${invoice.client.id}">
          Factures du client
        </a>
      </td>
    </tr>
  </c:forEach>
</table>

</body>
</html>
