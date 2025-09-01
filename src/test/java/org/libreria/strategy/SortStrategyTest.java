package org.libreria.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.libreria.model.Book;
import org.libreria.strategy.SortStrategy.AuthorSortStrategy;
import org.libreria.strategy.SortStrategy.InsertionOrderSortStrategy;
import org.libreria.strategy.SortStrategy.SortStrategy;
import org.libreria.strategy.SortStrategy.TitleSortStrategy;

import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SortStrategyTest {

    private Book bookA;
    private Book bookC;
    private Book bookB;
    private List<Book> originalList;

    @BeforeEach
    void setup() {
        bookA = new Book("BookA", "Author3", "1111111111", "Genre", 3, "Letto");
        bookC = new Book("BookC", "Author1", "3333333333", "Genre", 4, "In lettura");
        bookB = new Book("BookB", "Author2", "2222222222", "Genre", 2, "Da leggere");

        // Lista nell’ordine di inserimento
        originalList = Arrays.asList(bookA, bookC, bookB);
    }

    @Test
    void testInsertionOrderStrategy() {
        SortStrategy strategy = new InsertionOrderSortStrategy();
        List<Book> sorted = strategy.sort(List.copyOf(originalList));
        // Ordine di inserimento
        assertEquals(Arrays.asList(bookA, bookC, bookB), sorted);
    }

    @Test
    void testTitleSortStrategy() {
        SortStrategy strategy = new TitleSortStrategy();
        List<Book> sorted = strategy.sort(List.copyOf(originalList));
        // Ordine alfabetico per titolo: A → C → B
        assertEquals(Arrays.asList(bookA, bookB, bookC), sorted);
    }

    @Test
    void testAuthorSortStrategy() {
        SortStrategy strategy = new AuthorSortStrategy();
        List<Book> sorted = strategy.sort(List.copyOf(originalList));
        // Ordine alfabetico per autore: Author1 -> Author2 -> Author3
        assertEquals(Arrays.asList(bookC, bookB, bookA), sorted);
    }

}