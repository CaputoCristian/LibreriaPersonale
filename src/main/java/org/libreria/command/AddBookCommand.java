package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.model.Library;

public class AddBookCommand implements Command {
    private final Library library;
    private final Book book;

    public AddBookCommand(Library library, Book book) {
        this.library = library;
        this.book = book;
    }

    @Override
    public void execute() {
        library.addBook(book);
    }

    @Override
    public void undo() {
        library.removeBook(book.getIsbn());
    }
}
