package org.libreria.strategy.SortStrategy;

import java.util.List;

public class SortStrategyManager {
    private final List<SortStrategy> strategies;
    private int currentIndex = 0;

    public SortStrategyManager() {
        strategies = List.of(
                new InsertionOrderSortStrategy(),
                new TitleSortStrategy(),
                new AuthorSortStrategy()
        );
    }

    public SortStrategy nextStrategy() {
        currentIndex = (currentIndex + 1) % strategies.size();
        return getCurrentStrategy();
    }

    public SortStrategy getCurrentStrategy() {
        return strategies.get(currentIndex);
    }

    public void setCurrentStrategy(int strategyIndex) {
        this.currentIndex = strategyIndex;
    }
}
