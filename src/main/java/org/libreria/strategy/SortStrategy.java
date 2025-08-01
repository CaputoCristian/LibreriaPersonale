package org.libreria.strategy;

import org.libreria.model.Book;

import java.util.List;

public interface SortStrategy {
    
    String name = "";
    
    List<Book> sort(List<Book> books);

    String getName();
}
