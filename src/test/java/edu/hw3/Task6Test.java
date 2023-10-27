package edu.hw3;

import edu.hw3.task6.Stock;
import edu.hw3.task6.Task6;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import com.google.common.collect.ImmutableList;
import static org.assertj.core.api.Assertions.assertThat;

class Task6Test {
    private final List<Stock> stockExampleList = ImmutableList.of(
        new Stock("TCSG", 3483),
        new Stock("AFLT", 40),
        new Stock("GAZP", 179),
        new Stock("LKOH", 7444),
        new Stock("MTSS", 280),
        new Stock("ROSN", 586)
    );
    private final int expensiveStockCost = 7444;
    private final String expensiveStockName = "LKOH";
    private final Stock gazpStock = new Stock("GAZP", 179);
    private final List<Stock> stockExampleListWithoutGAZP = ImmutableList.of(
        new Stock("TCSG", 3483),
        new Stock("AFLT", 40),
        new Stock("LKOH", 7444),
        new Stock("MTSS", 280),
        new Stock("ROSN", 586)
    );

    @Test
    @DisplayName("Test that stock market adds stocks and returned true if all elements have been added")
    void testThatStockMarketAddsStocksAndReturnedTrueIfAllElementsHaveBeenAdded() {
        var stockMarket = new Task6();

        for (var stock : stockExampleList) {
            stockMarket.add(stock);
        }

        assertThat(stockMarket.getStockQueue()).containsAll(stockExampleList);
    }

    @Test
    @DisplayName("Test that the exchange returned the most valuables stock")
    void testThatTheExchangeReturnedTheMostValuablesStock() {
        var stockMarket = new Task6();

        for (var stock : stockExampleList) {
            stockMarket.add(stock);
        }
        var expensiveStock = stockMarket.mostValuableStock();
        assertThat(expensiveStock.getStockCost()).isEqualTo(expensiveStockCost);
        assertThat(expensiveStock.getStockName()).isEqualTo(expensiveStockName);
    }

    @Test
    @DisplayName(
        "Test that the exchange has deleted the stock and will returned true if it does not contain the deleted stock")
    void testThatTheExchangeHasDeletedTheStockAndWillReturnedTrueIfItDoesNotContainTheDeletedStock() {
        var stockMarket = new Task6();

        for (var stock : stockExampleList) {
            stockMarket.add(stock);
        }

        assertThat(stockMarket.getStockQueue()).contains(gazpStock);

        stockMarket.remove(gazpStock);
        assertThat(stockMarket.getStockQueue()).doesNotContain(gazpStock);
        assertThat(stockMarket.getStockQueue()).containsAll(stockExampleListWithoutGAZP);
    }

}
