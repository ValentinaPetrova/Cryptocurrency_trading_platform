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
  fetch('/api/crypto/top20')
    .then(response => response.json())
    .then(prices => {
      const tableBody = document.querySelector('#priceTable tbody');
      tableBody.innerHTML = '';

      for (const [symbol, price] of Object.entries(prices)) {
        const row = document.createElement('tr');
        row.innerHTML = `<td>${symbol}</td><td>${price}</td>`;
        tableBody.appendChild(row);
      }
    })
    .catch(error => {
      console.error('Error fetching prices:', error);
    });
}

// 3. Get Portfolio
function renderPortfolio(holdings, totalValue, profitLoss) {
  const summary = document.getElementById("portfolioSummary");
  const output = document.getElementById("portfolioOutput");

  summary.innerHTML = `
    <p><strong>Total Value:</strong> $${totalValue.toFixed(2)}</p>
    <p><strong>Profit/Loss:</strong> <span style="color:${profitLoss >= 0 ? 'green' : 'red'};">
      $${profitLoss.toFixed(2)}</span></p>
  `;

  if (!holdings || Object.keys(holdings).length === 0) {
    output.innerHTML = "<p>No holdings found.</p>";
    return;
  }

  const table = document.createElement("table");
  table.innerHTML = `
    <thead>
      <tr>
        <th>Symbol</th>
        <th>Quantity</th>
      </tr>
    </thead>
    <tbody>
      ${Object.entries(holdings).map(([symbol, quantity]) => `
        <tr>
          <td>${symbol}</td>
          <td>${parseFloat(quantity).toFixed(4)}</td>
        </tr>
      `).join("")}
    </tbody>
  `;
  output.innerHTML = "";
  output.appendChild(table);
}

function getPortfolio() {
  const userId = 1;

  // Fetch holdings
  fetch(`${baseUrl}/portfolio/${userId}/holdings`)
    .then(res => res.json())
    .then(holdings => {
      // Fetch current value and profit/loss in parallel
      Promise.all([
        fetch(`${baseUrl}/portfolio/${userId}/value`).then(r => r.json()),
        fetch(`${baseUrl}/portfolio/${userId}/profit-loss`).then(r => r.json())
      ]).then(([totalValue, profitLoss]) => {
        renderPortfolio(holdings, totalValue, profitLoss);
      });
    })
    .catch(err => {
      console.error("Error loading portfolio", err);
      document.getElementById("portfolioOutput").innerHTML = "<p>Error loading portfolio data.</p>";
    });
}


// 4. Execute Trade
function makeTrade() {
  const symbol = document.getElementById("symbol").value;
  const amount = parseFloat(document.getElementById("amount").value);
  const type = document.getElementById("type").value; // Should be 'buy' or 'sell'
  const userId = 1; // Example user ID

  fetch(`${baseUrl}/trade/${type}?userId=${userId}&symbol=${symbol}&quantity=${amount}`, {
    method: "POST"
  })
    .then(res => res.text()) // Because your controller returns plain text
    .then(data => {
      document.getElementById("tradeOutput").textContent = data;
    })
    .catch(err => console.error("Trade error", err));
}

// 5. Get Transactions
function getTransactions() {
  const userId = 1; // Hardcoded user ID
  fetch(`${baseUrl}/transactions/${userId}`)
    .then(res => res.json())
    .then(data => {
      const container = document.getElementById("transactionOutput");
      container.innerHTML = "";

      if (!Array.isArray(data) || data.length === 0) {
        container.innerHTML = "<p>No transactions found.</p>";
        return;
      }

      const table = document.createElement("table");
      table.innerHTML = `
        <thead>
          <tr>
            <th>Date</th>
            <th>Type</th>
            <th>Symbol</th>
            <th>Quantity</th>
            <th>Price (USD)</th>
            <th>Total (USD)</th>
          </tr>
        </thead>
        <tbody>
          ${data.map(tx => {
            const date = new Date(tx.timestamp || tx.TIMESTAMP).toLocaleString();
            const qty = parseFloat(tx.quantity || tx.QUANTITY);
            const price = parseFloat(tx.price || tx.PRICE);
            const total = (qty * price).toFixed(2);
            return `
              <tr>
                <td>${date}</td>
                <td>${tx.type || tx.TYPE}</td>
                <td>${tx.symbol || tx.SYMBOL}</td>
                <td>${qty.toFixed(4)}</td>
                <td>$${price.toFixed(2)}</td>
                <td>$${total}</td>
              </tr>
            `;
          }).join("")}
        </tbody>
      `;
      container.appendChild(table);
    })
    .catch(err => {
      console.error("Transactions error", err);
      document.getElementById("transactionOutput").textContent = "Failed to load transactions.";
    });
}

//6. Reset
function resetAccount() {
  fetch(`${baseUrl}/transactions/reset?userId=1`, {
    method: "POST"
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      getBalance(); // Refresh balance
      getPortfolio(); // Refresh portfolio
    })
    .catch(err => console.error("Reset error", err));
}

