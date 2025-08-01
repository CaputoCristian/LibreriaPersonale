package org.libreria.template;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

import javax.swing.*;

public class AddBookDialog extends BookDialogTemplate {

    private Book bookResult;

    public AddBookDialog(JFrame parent) {
        super(parent, "Aggiungi Libro");
    }

    @Override
    protected void onConfirm() {
        if (!validateCommonFields()) return;

        String isbn = isbnField.getText().trim();
        boolean exists = LibrarySingleton.getInstance()
                .getLibrary()
                .getBooks()
                .stream()
                .anyMatch(b -> b.getIsbn().equals(isbn));

        if (exists) {
            JOptionPane.showMessageDialog(this, "Esiste già un libro con questo ISBN.", "ISBN duplicato", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
//            bookResult = BookFactory.create(
//                    titleField.getText().trim(),
//                    authorField.getText().trim(),
//                    isbn,
//                    genreField.getText().trim(),
//                    Integer.parseInt(ratingField.getText().trim()),
//                    readingStatusField.getText().trim()

            bookResult = new Book();
            bookResult.setTitle(titleField.getText().trim());
            bookResult.setAuthor(authorField.getText().trim());
            bookResult.setIsbn(isbn);                           //già definito sopra per controllo
            bookResult.setGenre(genreField.getText().trim());
            bookResult.setRating(Integer.parseInt(ratingField.getText().trim()));
            bookResult.setReadingStatus(readingStatusField.getText().trim());

            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Book showDialog() {
        setVisible(true);
        return bookResult;
    }
}