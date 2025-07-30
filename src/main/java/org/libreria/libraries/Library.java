package org.libreria.libraries;

import org.libreria.books.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Library extends AbstractLibrary {

    public Library() {
        super(new ArrayList<>());
    }

    public Library(List<Book> books) {
        super(books);
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public boolean removeBook(Book book) {
        return books.remove(book);
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(b -> b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public void sortByTitle() {
        books.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));
    }

    @Override
    public void sortByRating() {
        books.sort(Comparator.comparingInt(Book::getRating).reversed());
    }
}