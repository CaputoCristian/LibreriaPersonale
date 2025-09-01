package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

public class AddBookCommand implements CommandInterface {
    private final Book book;

    public AddBookCommand(Book book) {
        this.book = book;
    }

    @Override
    public void execute() {

        LibrarySingleton.getInstance().addBook(book);
    }

    @Override
    public void undo() {
        LibrarySingleton.getInstance().removeBook(book.getIsbn());
    }
}
