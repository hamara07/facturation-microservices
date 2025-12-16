
<html>
<head>
  <title>Total facturé</title>
  <style>
    body { font-family: Arial; background: #fafafa; padding: 20px; }
    .box { background: white; padding: 20px; width: 400px;
      border-radius: 5px; box-shadow: 0 0 8px #ccc; }
    .btn { display: inline-block; margin-top: 20px; padding: 10px 15px;
      background: #007bff; color: white; border-radius: 4px;
      text-decoration: none; }
  </style>
</head>

<body>

<div class="box">
  <h2>Total facturé du client #${clientId}</h2>

  <h3>${totalAmount} FCFA</h3>

  <a class="btn" href="/client/${clientId}">
    Retour aux factures
  </a>
</div>

</body>
</html>
