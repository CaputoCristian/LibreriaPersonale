package org.libreria.model;

import org.libreria.DTO.BookUpdateDTO;

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
    public abstract boolean removeBook(String isbn);
    public abstract boolean updateBook(String isbn, BookUpdateDTO updateDTO);

//    public abstract List<Book> searchByTitle(String title);
//    public abstract List<Book> searchByAuthor(String author);
//    public abstract void sortByTitle();
//    public abstract void sortByRating();
}
