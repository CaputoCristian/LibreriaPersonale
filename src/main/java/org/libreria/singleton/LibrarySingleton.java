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
    private SearchStrategy searchStrategy;
    private SortStrategy sortStrategy;
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
        for (Book book : books) {
            library.addBook(book);
        }
    }

    public void saveBooksToJson() {
        JsonHandler.saveBooks(jsonFile, library.getBooks());
    }

    public void setSearchStrategy(SearchStrategy searchStrategy) {
        this.searchStrategy = searchStrategy;
    }

    public void setSortStrategy(SortStrategy sortStrategy) {
        this.sortStrategy = sortStrategy;
    }

    public List<Book> search(List<Book> books, String keyword) {
        if (searchStrategy == null) throw new IllegalStateException("Search strategy not set");
        return searchStrategy.search(books, keyword);
    }

    public List<Book> sort(List<Book> books) {
        if (sortStrategy == null) throw new IllegalStateException("Sort strategy not set");
        return sortStrategy.sort(books);
    }

}
