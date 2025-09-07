package org.libreria.strategy.SortStrategy;

import org.libreria.model.Book;

import java.util.ArrayList;
import java.util.List;

public class InsertionOrderSortStrategy implements SortStrategy {
    @Override
    public List<Book> sort(List<Book> books) {
        return new ArrayList<>(books); // Di default si visualizzano come sono caricati, no ordinamento
    }

    @Override
    public String getName() {
        return "Inserimento";
    }
}