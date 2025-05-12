const baseUrl = "http://localhost:8080/api";

// 1. Get Balance
function getBalance() {
  const userId = 1; // Replace with dynamic value (you could collect it from an input field or session)
  fetch(`${baseUrl}/user/balance?userId=${userId}`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("balanceOutput").textContent = JSON.stringify(data, null, 2);
    })
    .catch(err => console.error("Balance error", err));
}

// 2. Get Prices
function getPrices() {
  fetch(`${baseUrl}/crypto/prices`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("priceOutput").textContent = JSON.stringify(data, null, 2);
    })
    .catch(err => console.error("Prices error", err));
}

// 3. Get Portfolio
function getPortfolio() {
  fetch(`${baseUrl}/portfolio`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("portfolioOutput").textContent = JSON.stringify(data, null, 2);
    })
    .catch(err => console.error("Portfolio error", err));
}

// 4. Execute Trade
function makeTrade() {
  const symbol = document.getElementById("symbol").value;
  const amount = parseFloat(document.getElementById("amount").value);
  const type = document.getElementById("type").value;

  fetch(`${baseUrl}/trade`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ symbol, amount, type })
  })
    .then(res => res.json())
    .then(data => {
      document.getElementById("tradeOutput").textContent = JSON.stringify(data, null, 2);
    })
    .catch(err => console.error("Trade error", err));
}

// 5. Get Transactions
function getTransactions() {
  fetch(`${baseUrl}/transactions`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("transactionOutput").textContent = JSON.stringify(data, null, 2);
    })
    .catch(err => console.error("Transactions error", err));
}
