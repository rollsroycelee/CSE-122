package Project4;

// Royce
// 1/23/2024
// CSE 122
// TA: Maggie
// This class prompts the user Buy, Sell stocks, or save or quit. 
// This class also tracks the value of their portfolio when they finish buying and selling stocks.

import java.io.*;
import java.util.*;

public class Stonks {
    public static final String STOCKS_FILE_NAME = "stonks.tsv";
    public static void main(String[] args) throws FileNotFoundException {
        Scanner fileScan = new Scanner(new File (STOCKS_FILE_NAME));
        Scanner console = new Scanner(System.in);

        //Initialize values
        int numStocks = Integer.parseInt(fileScan.nextLine());
        String[] stocks = new String[numStocks];
        double[] price = new double[numStocks];
        double[] portfolio = new double[numStocks];
        double totalPort = 0;

        // Load in file and populate the above values
        loadFile(fileScan, stocks, price);

        //INTRODUCTION
        System.out.println("Welcome to the CSE 122 Stocks Simulator!");
        System.out.println("There are " + numStocks + " stocks on the market:");
        
        //SHOWS ALL STOCK OPTIONS/priceS
        for (int i = 0; i < stocks.length;i++){
            System.out.println(stocks[i] + ": " + price[i]);
        }

        String choice = "";
        while (!choice.equalsIgnoreCase("Q")) {
            System.out.println();
            System.out.println("Menu: (B)uy, (Se)ll, (S)ave, (Q)uit");
            System.out.print("Enter your choice: ");
            choice = console.nextLine();

            if (choice.equalsIgnoreCase("B")) {
                // WHEN THEY BUY 
                totalPort = buyStock(console, stocks, price, portfolio, totalPort);
                

            } else if (choice.equalsIgnoreCase("Se")) {
                // WHEN THEY SELL
                totalPort = sellStock(console, stocks, price, portfolio, totalPort);

            } else if (choice.equalsIgnoreCase("S")) {
                //Implement save
                System.out.print("Enter new portfolio file name: ");
                save(console, stocks, portfolio);

            } else if (!choice.equalsIgnoreCase("Q")) {
                System.out.println("Invalid choice: " + choice);
                System.out.println("Please try again");

            }
        }

        System.out.println();
        double scale = Math.pow(10,4);

        double rounded = Math.round(totalPort * scale) / scale;
        System.out.println("Your portfolio is currently valued at: $" + rounded);
    }




//Behavior: 
// - Reads data from a given `Scanner` object, populating the provided `stocks` array with stock 
//   names and the `price` array with their corresponding prices. Each line of input is expected 
//   to contain a stock name followed by its price.
//Returns: 
// - This method does not return any value; it populates the `stocks` and `price` arrays directly.
//Parameters:
// - fileScan: A Scanner object used to read lines of stock data from the input source.
// - stocks: A String[] array that will be populated with stock names from the input.
// - price: A double[] array that will be populated with corresponding stock prices from the input.
    public static void loadFile(Scanner fileScan, String[] stocks, double[] price){
        int index = 0;
        fileScan.nextLine();

        while(fileScan.hasNextLine()) {
            String line = fileScan.nextLine();
            Scanner lineScan = new Scanner(line);
            
            stocks[index] = lineScan.next();
            price[index] = lineScan.nextDouble();
            index++;
        }
    }
    

//Behavior: 
// - Searches for the specified stock name within the provided array of stock names. 
//   Returns the index of the first occurrence of the stock name if found. If the 
//   stock name is not found, returns -1.
//Returns: 
// - An integer representing the index of the stock name in the array if found, or -1 if the stock
// -  name is not present in the array.
//Parameters:
// - stocks: An array of strings containing stock names to search through.
// - name: A string representing the stock name to search for in the array.
    public static int indexOf(String[] stocks, String name) {
        int index = -1;
        for (int i = 0; i < stocks.length; i++) {
            if (stocks[i].equals(name)) {
                index = i;
            }
        }
        return index; 
    }

//Behavior: 
// - Allows a user to buy a specified stock by entering its ticker and their budget. The method
//   calculates the number of shares that can be purchased based on the stock's price and updates
//   the user's portfolio and total portfolio value. If the budget is less than $5, 
//   the purchase is not processed.
//Returns: 
// - This method does returns the updated value of totalPort so that this value could be used
//   later in the code.
//Parameters:
// - console: A Scanner object used to obtain user input for the stock ticker and budget.
// - stocks: An array of strings containing available stock tickers.
// - price: A double array containing prices corresponding to the stock tickers.
// - portfolio: A double array representing the number of shares owned for each stock.
// - totalPort: A double where it represents the total value of all purchases.
    public static double buyStock(Scanner console, String[] stocks, double[] price, 
                                double[] portfolio, double totalPort){
        System.out.print("Enter the stock ticker: ");
        String name = console.nextLine();

        System.out.print("Enter your budget: ");
        double budget = Double.parseDouble(console.nextLine());

        if ( budget < 5){
            System.out.println("Budget must be at least $5");
        } else {
            for (int i = 0; i < stocks.length; i++){
                if (stocks[i].equals(name)){
                    double shares = budget/price[i];
                    portfolio[i] += shares;
                }
            }
            System.out.println("You successfully bought " + name + ".");
            totalPort += budget;
            
        }
        return totalPort;
    }

//Behavior: 
// - Allows a user to sell a specified number of shares for a given stock. The method checks if
//   the user owns enough shares to complete the sale. If the sale is valid, it updates the 
//   portfolio and the total portfolio value. If the user does not have enough shares, a 
//   message is displayed indicating the issue.
//Returns: 
// - This method returns the updated value of totalPort so that this value can be used later in 
//   the code.
//Parameters:
// - console: A Scanner object used to obtain user input for the stock ticker and the number 
//           of shares to sell.
// - stocks: An array of strings containing available stock tickers.
// - price: A double array containing prices corresponding to the stock tickers.
// - portfolio: A double array representing the number of shares owned for each stock,
//              which is updated after a sale.
// - totalPort: A double where it represents the total value of all purchases.
    public static double sellStock(Scanner console, String[] stocks, double[] price, 
                                 double[] portfolio, double totalPort) {
        System.out.print("Enter the stock ticker: ");
        String name = console.nextLine();
        System.out.print("Enter the number of shares to sell: ");
        double shares = Double.parseDouble(console.nextLine());

        for(int i = 0; i < stocks.length; i++){
            if (name.equals(stocks[i])){
                if (shares > portfolio[i]){
                    System.out.println("You do not have enough shares of " + stocks[i] + 
                                        " to sell " + shares + " shares.");
                } else {
                    portfolio[i] -= shares;

                    double valueSold = shares * price[i];
                    totalPort -= valueSold;
                    System.out.println("You successfully sold " + shares + 
                                        " shares of " + stocks[i] + ".");
                }
            }
        }
        return totalPort;
    }

//Behavior: 
// - Saves the portfolio data to a specified file. Prompts the user to enter a file name,
//    and then writes the stock tickers and the number of shares owned for stocks with a 
//    non-zero portfolio balance to the file.
//Returns: 
// - This method does not return any value. It writes the portfolio data directly to the 
//   specified file.
//Parameters:
// - console: A Scanner object used to obtain user input for the file name where the portfolio
//            will be saved.
// - stocks: An array of strings containing stock tickers.
// - price: A double array containing prices corresponding to the stock tickers 
//          (not used in this method).
// - portfolio: A double array representing the number of shares owned for each stock. 
//              Stocks with zero shares are not written to the file.
public static void save(Scanner console, String[] stocks, double[] portfolio) 
                        throws FileNotFoundException {
        // 1. Make the File & PrintStream
        String fileName = console.nextLine();
        PrintStream output = new PrintStream(new File(fileName));

        for (int i = 0; i < portfolio.length; i++){
            if (portfolio[i] > 0){
                output.println(stocks[i] + " " + portfolio[i]);
            }
        }
    }
}