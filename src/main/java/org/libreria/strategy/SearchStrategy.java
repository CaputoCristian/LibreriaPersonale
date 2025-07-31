package org.libreria.strategy;

import org.libreria.model.Book;

import java.util.List;

public interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}
