package edu.hw3.task6;

import java.util.Objects;

/**
 * Class of the stock model.
 */
public class Stock implements Comparable<Stock> {
    private final String stockName;
    private final Integer stockCost;

    /**
     * Class constructor.
     *
     * @param stockName stock ticker.
     * @param stockCost stock cost.
     */
    public Stock(String stockName, Integer stockCost) {
        this.stockName = stockName;
        this.stockCost = stockCost;
    }

    public String getStockName() {
        return stockName;
    }

    public Integer getStockCost() {
        return stockCost;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Stock stock = (Stock) o;
        return Objects.equals(stockName, stock.stockName) && Objects.equals(stockCost, stock.stockCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockName, stockCost);
    }

    @Override
    public int compareTo(Stock otherStock) {
        if (this.stockCost.equals(otherStock.stockCost)) {
            return this.stockName.compareTo(otherStock.getStockName());
        }
        int compare = this.stockCost.compareTo(otherStock.getStockCost());
        return (compare > 0) ? -1 : 1;

    }
}
