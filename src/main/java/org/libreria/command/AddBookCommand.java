package org.libreria.command;

import org.libreria.DTO.BookUpdateDTO;
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
        Book newBook = new Book(book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.getRating(), book.getReadingStatus()); //si ha un DTO con tutto modificato, in quanto è un'aggiunta
        library.addBook(newBook);
    }

    @Override
    public void undo() {
        library.removeBook(book.getIsbn());
    }
}
