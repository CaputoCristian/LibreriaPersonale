package org.libreria.strategy;

import org.libreria.model.Book;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AuthorSortStrategy implements SortStrategy {
    @Override
    public List<Book> sort(List<Book> books) {
        return books.stream()
                .sorted(Comparator.comparing(Book::getAuthor))
                .collect(Collectors.toList());
    }

    @Override
    public String getName() {
        return "Autore";
    }

}