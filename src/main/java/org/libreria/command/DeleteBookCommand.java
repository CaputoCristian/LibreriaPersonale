package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

public class DeleteBookCommand implements CommandInterface {
    private final String isbn;
    private Book deletedBook;

    public DeleteBookCommand(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        deletedBook = LibrarySingleton.getInstance().getBookByIsbn(isbn);
        LibrarySingleton.getInstance().removeBook(isbn);
    }

    @Override
    public void undo() {
        if (deletedBook != null) {
            LibrarySingleton.getInstance().addBook(deletedBook);
        }
    }
}