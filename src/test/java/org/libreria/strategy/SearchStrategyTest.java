package org.libreria.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.libreria.model.Book;
import org.libreria.model.SearchFilter;
import org.libreria.strategy.SearchStrategy.AuthorSearchStrategy;
import org.libreria.strategy.SearchStrategy.FilteredSearchStrategy;
import org.libreria.strategy.SearchStrategy.SearchStrategy;
import org.libreria.strategy.SearchStrategy.TitleSearchStrategy;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchStrategyTest {

    private Book bookA;
    private Book bookC;
    private Book bookB;
    private List<Book> originalList;

    @BeforeEach
    void setup() {
        bookA = new Book("BookA", "Author1", "1111111111", "Genre1", 3, "Letto");
        bookB = new Book("BookB", "Author2", "2222222222", "Genre2", 2, "Da leggere");
        bookC = new Book("BookC", "Author3", "3333333333", "Genre3", 4, "In lettura");

        // Lista nell’ordine di inserimento
        originalList = Arrays.asList(bookA, bookC, bookB);
    }


    @Test
    void testSearchByTitle() {
        String searchTerm = "BookA";
        List<Book> result = new TitleSearchStrategy().search(originalList, searchTerm);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(b -> b.getTitle().toLowerCase().contains("booka")));
    }

    @Test
    void testSearchByAuthor() {
        String searchTerm = "Author2";
        List<Book> result = new AuthorSearchStrategy().search(originalList, searchTerm);

        assertEquals(1, result.size());
        assertTrue(result.stream().allMatch(b -> b.getAuthor().toLowerCase().contains("author")));
    }

    @Test
    void testFilteredSearchByStatus() {
        boolean byTitle = true;
        String searchTerm = "Book";
        String readingStatusFilter = "Letto";

        SearchFilter filter = new SearchFilter(searchTerm, byTitle, readingStatusFilter, null);

        SearchStrategy base = new TitleSearchStrategy();
        SearchStrategy filtered = new FilteredSearchStrategy(base, filter.getReadingStatusFilter(), filter.getMinRating());

        List<Book> result = filtered.search(originalList, filter.getSearchTerm());

        assertEquals(1, result.size());
        assertEquals("BookA", result.get(0).getTitle());
    }

    @Test
    void testFilteredSearchByRating() {
        boolean byTitle = false;
        String searchTerm = "Author";
        Integer minRating = 4;

        SearchFilter filter = new SearchFilter(searchTerm, byTitle, null, minRating);

        SearchStrategy base = new AuthorSearchStrategy();
        SearchStrategy filtered = new FilteredSearchStrategy(base, filter.getReadingStatusFilter(), filter.getMinRating());

        List<Book> result = filtered.search(originalList, filter.getSearchTerm());

        assertEquals(1, result.size());
        assertEquals("BookC", result.get(0).getTitle());
    }

    @Test
    void testFilteredSearchByStatusAndRating() {
        boolean byTitle = false;
        String searchTerm = "Author";
        String readingStatusFilter = "Da leggere";
        Integer minRating = 2;

        SearchFilter filter = new SearchFilter(searchTerm, byTitle, readingStatusFilter, minRating);

        SearchStrategy base = new AuthorSearchStrategy();
        SearchStrategy filtered = new FilteredSearchStrategy(base, filter.getReadingStatusFilter(), filter.getMinRating());

        List<Book> result = filtered.search(originalList, filter.getSearchTerm());

        assertEquals(1, result.size());
        assertEquals("BookB", result.get(0).getTitle());
    }

    @Test
    void testNoResults() {
        String searchTerm = "Nessun risultato";
        List<Book> result = new TitleSearchStrategy().search(originalList, searchTerm);

        assertTrue(result.isEmpty());
    }
}
