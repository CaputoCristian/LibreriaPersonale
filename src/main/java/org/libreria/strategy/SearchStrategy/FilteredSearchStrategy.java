package org.libreria.strategy.SearchStrategy;

import org.libreria.model.Book;

import java.util.List;

public class FilteredSearchStrategy implements SearchStrategy {
    private final SearchStrategy base;
    private final String readingStatus;
    private final Integer minRating;

    public FilteredSearchStrategy(SearchStrategy base, String readingStatus, Integer minRating) {
        this.base = base;
        this.readingStatus = readingStatus;
        this.minRating = minRating;
    }

    @Override
    public List<Book> search(List<Book> books, String keyword) {
        return base.search(books, keyword).stream()
                .filter(book -> readingStatus == null || book.getReadingStatus().equalsIgnoreCase(readingStatus))
                .filter(book -> minRating == null || book.getRating() >= minRating)
                .toList();
    }
}