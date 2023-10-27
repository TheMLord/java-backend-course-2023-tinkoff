package edu.hw3.task6;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Class Task6.
 */
public class Task6 implements StockMarket {
    private final Queue<Stock> stockQueue = new PriorityQueue<>();

    @Override
    public void add(Stock stock) {
        this.stockQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        this.stockQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stockQueue.peek();
    }

    public Queue<Stock> getStockQueue() {
        return stockQueue;
    }

}
