# How to Use the Application
The Crypto Trading Platform is a simulation tool for buying, selling, and tracking cryptocurrency activity. Below are step-by-step instructions for using each feature via the interface.

## View Account Balance
- Click the "Get Balance" button.

- The current account balance will be displayed below the button.

## Get Latest Cryptocurrency Prices
- Click the "Get Last Prices" button.

- A table will appear showing the latest USD prices for various cryptocurrencies.

- To refresh and retrieve updated prices, click the button again.

## View Portfolio
- Click the "Get Portfolio" button.

- It will show your current holdings and quantities in different cryptocurrencies.

- To reflect recent transactions (buys/sells), click this button again to refresh.

## Make a Trade (Buy or Sell)
- Enter the cryptocurrency symbol (e.g., BTC/USD) in the input field.

- Input the amount you wish to buy or sell.

- Choose the trade type (Buy or Sell) from the dropdown.

- Click the "Submit Trade" button.

~💡 If you try to buy more than your available balance or submit an invalid input, a clear error message will be shown. ~

## View Transaction History
- Click the "View Transactions" button.

- A table will display a list of all recent transactions for the user, including type, quantity, symbol, and price.

## Reset Account
- Click the "Reset Account" button.

This will:

- Clear the transaction history.

- Reset the account balance to its initial value.

- A confirmation message will appear indicating the operation was successful.

## Security Note
For development and testing purposes, the platform currently assumes a single demo user with userId = 1.
No login or password is required at this stage to interact with the interface or backend APIs.

When deployed in a real environment, the following enhancements are expected:

- Authentication and authorization using JWT or OAuth2

- User-specific sessions and secure login handling

- Database constraints to support multi-user operations and secure access
