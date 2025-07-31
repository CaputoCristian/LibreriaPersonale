package org.libreria.command;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.model.Book;
import org.libreria.model.Library;

public class UpdateBookCommand implements Command {
    private final Library library;
    private final BookUpdateDTO dto;

    private Book oldBookBackup;
    private Book bookToUpdate;

    public UpdateBookCommand(Library library, BookUpdateDTO dto) {
        this.library = library;
        this.dto = dto;    }

    @Override
    public void execute() {
        // Cerca il libro da aggiornare tramite ISBN (immutabile)
        bookToUpdate = library.getBooks().stream()
                .filter(b -> b.getIsbn().equals(dto.getIsbn()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found with ISBN: " + dto.getIsbn()));

        // Backup profondo (deep copy)
        oldBookBackup = new Book(
                bookToUpdate.getTitle(),
                bookToUpdate.getAuthor(),
                bookToUpdate.getIsbn(),
                bookToUpdate.getGenre(),
                bookToUpdate.getRating(),
                bookToUpdate.getReadingStatus()
        );

        library.updateBook(dto.getIsbn(), dto);

//        // Applica i nuovi dati dal DTO
//        bookToUpdate.setTitle(dto.getTitle());
//        bookToUpdate.setAuthor(dto.getAuthor());
//        bookToUpdate.setGenre(dto.getGenre());
//        bookToUpdate.setRating(dto.getRating());
//        bookToUpdate.setReadingStatus(dto.getReadingStatus());


    }

    @Override
    public void undo() {
        if (oldBookBackup != null) {
            library.updateBook(oldBookBackup.getIsbn(), new BookUpdateDTO( oldBookBackup ) );
        }
    }
}
