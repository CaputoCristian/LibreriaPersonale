package org.libreria.libraries;

import org.libreria.books.Book;

import java.util.List;

public abstract class AbstractLibrary {

    protected List<Book> books;

    public AbstractLibrary() {
    }

    public AbstractLibrary(List<Book> books) {
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public abstract void addBook(Book book);
    public abstract boolean removeBook(Book book);
    public abstract List<Book> searchByTitle(String title);
    public abstract List<Book> searchByAuthor(String author);
    public abstract void sortByTitle();
    public abstract void sortByRating();
}
