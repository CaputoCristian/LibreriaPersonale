package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.model.Library;

public class DeleteBookCommand implements CommandInterface {
    private final Library library;
    private final String isbn;
    private Book deletedBook;

    public DeleteBookCommand(Library library, String isbn) {
        this.library = library;
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        deletedBook = library.getBookByIsbn(isbn);
        library.removeBook(isbn);
    }


    @Override
    public void undo() {
        if (deletedBook != null) {
            library.addBook(deletedBook);
        }
    }
}