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

//        System.out.println("SetStrategy AUTOMATICO da: " + strategies.get(currentIndex).getName() + ", a: " + strategies.get((currentIndex + 1) % strategies.size()).getName());

        currentIndex = (currentIndex + 1) % strategies.size();
        return getCurrentStrategy();
    }

    public SortStrategy getCurrentStrategy() {

//        System.out.println("getStrategy: " + strategies.get(currentIndex).getName());

        return strategies.get(currentIndex);
    }

    public void setCurrentStrategy(int strategyIndex) {

//        System.out.println("SetStrategy MANUALE da: " + strategies.get(currentIndex).getName() + ", a: " + strategies.get(strategyIndex).getName());

        this.currentIndex = strategyIndex;
    }
}
