package org.libreria.command;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

public class UpdateBookCommand implements CommandInterface {

    private final Book updatedBook;

    private Book bookToUpdate;

    public UpdateBookCommand(Book updatedBook) {
        this.updatedBook = updatedBook;    }

    @Override
    public void execute() {

        // Cerca nel database il libro originale tramite isbn (immutabile)
        bookToUpdate = LibrarySingleton.getInstance().getLibrary().getBooks().stream()
                .filter(b -> b.getIsbn().equals(updatedBook.getIsbn()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + updatedBook.getIsbn()));

        // Backup profondo
        bookToUpdate = new Book(
                bookToUpdate.getTitle(),
                bookToUpdate.getAuthor(),
                bookToUpdate.getIsbn(),
                bookToUpdate.getGenre(),
                bookToUpdate.getRating(),
                bookToUpdate.getReadingStatus()
        );
        LibrarySingleton.getInstance().updateBook(bookToUpdate.getIsbn(), updatedBook);

    }

    @Override
    public void undo() {
        if (bookToUpdate != null) {
            LibrarySingleton.getInstance().updateBook(bookToUpdate.getIsbn(), bookToUpdate);
        }
    }
}
