package org.libreria.model;

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

    public abstract Book getBookByIsbn(String isbn);

    public abstract void addBook(Book book);
    public abstract void removeBook(String isbn);
    public abstract void updateBook(String isbn, Book updatedBook);

}
