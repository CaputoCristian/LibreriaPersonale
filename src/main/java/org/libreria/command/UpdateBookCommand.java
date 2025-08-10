package org.libreria.command;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.model.Book;
import org.libreria.model.Library;
import org.libreria.singleton.LibrarySingleton;

public class UpdateBookCommand implements CommandInterface {

    private final Book updatedBook;

    private Book oldBookBackup;
    private Book bookToUpdate;

    public UpdateBookCommand(Book updatedBook) {
        this.updatedBook = updatedBook;    }

    @Override
    public void execute() {

        // Cerca il libro da aggiornare tramite ISBN (immutabile) per backup e per controllare che esista
        bookToUpdate = LibrarySingleton.getInstance().getLibrary().getBooks().stream()
                .filter(b -> b.getIsbn().equals(updatedBook.getIsbn()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + updatedBook.getIsbn()));

        // Backup profondo (deep copy)
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
        if (oldBookBackup != null) {
            LibrarySingleton.getInstance().updateBook(oldBookBackup.getIsbn(), oldBookBackup);
        }
    }
}
