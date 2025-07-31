package org.libreria.strategy;

import org.libreria.model.Book;

import java.util.List;

public interface SortStrategy {
    List<Book> sort(List<Book> books);
}
