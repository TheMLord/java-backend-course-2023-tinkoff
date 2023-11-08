package edu.hw3.task6;

/**
 * Stock Market Contract.
 */
public interface StockMarket {
    /**
     * Method that adds a new stock to the stock market.
     *
     * @param stock any stock.
     */
    void add(Stock stock);

    /**
     * Method that removes a stock from the stock market.
     *
     * @param stock any stock.
     */
    void remove(Stock stock);

    /**
     * Method that returns the most valuable stock.
     *
     * @return the  most valuable stock.
     */
    Stock mostValuableStock();
}
