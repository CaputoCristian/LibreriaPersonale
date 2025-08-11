package org.libreria.strategy.SortStrategy;

import org.libreria.model.Book;

import java.util.List;

public interface SortStrategy {
    List<Book> sort(List<Book> books);
    String getName();
}
