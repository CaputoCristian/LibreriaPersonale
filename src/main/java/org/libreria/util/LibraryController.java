package org.libreria.util;

import org.libreria.command.AddBookCommand;
import org.libreria.command.CommandInterface;
import org.libreria.command.DeleteBookCommand;
import org.libreria.command.UpdateBookCommand;
import org.libreria.model.Book;
import org.libreria.model.Library;
import org.libreria.model.SearchFilter;
import org.libreria.singleton.LibrarySingleton;
import org.libreria.strategy.SearchStrategy.AuthorSearchStrategy;
import org.libreria.strategy.SearchStrategy.FilteredSearchStrategy;
import org.libreria.strategy.SearchStrategy.SearchStrategy;
import org.libreria.strategy.SearchStrategy.TitleSearchStrategy;
import org.libreria.strategy.SortStrategy.SortStrategy;

import java.util.List;

public class LibraryController {

    public LibraryController() {
        LibrarySingleton.getInstance().loadBooksFromJson();
    }

    public void addBook(Book newBook) {
        CommandInterface command = new AddBookCommand(newBook);
        command.execute();
        save();
    }

    public void updateBook(Book updatedBook) {
        CommandInterface command = new UpdateBookCommand(updatedBook);
        command.execute();
        save();
    }

    public void deleteBook(String isbn) {
        CommandInterface command = new DeleteBookCommand(isbn);
        command.execute();
        save();
    }

    public List<Book> searchBooks(SearchFilter filter) {

        if (filter != null) {
            SearchStrategy baseStrategy = filter.isSearchByTitle()
                    ? new TitleSearchStrategy()
                    : new AuthorSearchStrategy();

            SearchStrategy fullStrategy = new FilteredSearchStrategy(
                    baseStrategy,
                    filter.getReadingStatusFilter(),
                    filter.getMinRating()
            );
            return fullStrategy.search(LibrarySingleton.getInstance().getLibrary().getBooks(), filter.getSearchTerm());
        }

        return null;
    }

    public List<Book> sortBooks(SortStrategy strategy) {
        return strategy.sort(LibrarySingleton.getInstance().getLibrary().getBooks());
    }

    private void save() {
        LibrarySingleton.getInstance().saveBooksToJson();
    }
}
