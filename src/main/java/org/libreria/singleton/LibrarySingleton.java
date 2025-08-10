package org.libreria.singleton;

import org.libreria.model.Book;
import org.libreria.model.Library;
import org.libreria.strategy.SearchStrategy.SearchStrategy;
import org.libreria.strategy.SortStrategy.SortStrategy;
import org.libreria.util.JsonHandler;

import java.io.File;
import java.util.List;

public class LibrarySingleton {

    private static LibrarySingleton instance;
    private Library library;
    private File jsonFile;

    private LibrarySingleton() {
        library = new Library();
    }

    public static synchronized LibrarySingleton getInstance() {
        if (instance == null) {
            instance = new LibrarySingleton();
        }
        return instance;
    }

    public void setSource(File jsonFile) {
        this.jsonFile = jsonFile;
    }


    public Library getLibrary() {
        return library;
    }

    public void loadBooksFromJson() {
        List<Book> books = JsonHandler.loadBooks(jsonFile);
        library = new Library(books);

    }

    public void addBook(Book book) {
        library.addBook(book);
    }

    public Book getBookByIsbn(String isbn) {
        return library.getBookByIsbn(isbn);
    }

    public void removeBook(String isbn) {
        library.removeBook(isbn);
    }

    public void updateBook(String isbn, Book updatedBook) {
        library.updateBook(isbn, updatedBook);
    }

    public void saveBooksToJson() {
        JsonHandler.saveBooks(jsonFile, library.getBooks());
    }

}
