<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
  <title>Payer la facture #${invoice.id}</title>
  <style>
    body { font-family: Arial; background: #fafafa; padding: 20px; }
    form { background: white; padding: 20px; width: 400px;
      border-radius: 5px; box-shadow: 0 0 8px #ccc; }
    label { display: block; margin-top: 10px; }
    input, select { width: 100%; padding: 8px; margin-top: 5px; }
    .btn { margin-top: 15px; padding: 10px; width: 100%;
      background: #28a745; color: white; border: none;
      border-radius: 4px; cursor: pointer; }
  </style>
</head>

<body>

<h2>Payer la facture #${invoice.id}</h2>

<form method="post" action="/invoice/${invoice.id}/pay">
  <label>Date de paiement :</label>
  <input type="date" name="datePaiement" required>

  <label>Méthode de paiement :</label>
  <select name="paymentMethod" required>
    <option value="CARD">Carte bancaire</option>
    <option value="TRANSFER">Virement</option>
    <option value="CASH">Espèces</option>
  </select>

  <button class="btn" type="submit">Payer</button>
</form>

</body>
</html>
