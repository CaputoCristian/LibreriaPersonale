package org.libreria.model;

public class Book extends AbstractBook {

    public Book() {
        super();
    }

    public Book(String title, String author, String isbn, String genre, Integer rating, String readingStatus) {
        super(title, author, isbn, genre, rating, readingStatus);
    }

    @Override
    public String toString() {
        return String.format("%s di %s [%s] - Genere: %s, Rating: %d, Stato: %s",
                title, author, isbn, genre, rating, readingStatus);
    }
}
