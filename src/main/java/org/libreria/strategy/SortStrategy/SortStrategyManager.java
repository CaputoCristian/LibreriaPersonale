package org.libreria.strategy.SortStrategy;

import java.util.List;

public class SortStrategyManager {
    private final List<SortStrategy> strategies;
    private int currentIndex = -1;

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
        if (currentIndex == -1 || currentIndex == strategies.size()) return null; //se null si usa l'ordinamento di default
        return strategies.get(currentIndex);
    }

}
