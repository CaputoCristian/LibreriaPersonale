package org.libreria.template;

import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

import javax.swing.*;

public class AddBookDialog extends BookDialogTemplate {

    private Book newBook;

    public AddBookDialog(JFrame parent) {
        super(parent, "Aggiungi Libro");
    }

    @Override
    protected void onConfirm() {
        if (!validateFields()) return;

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

            newBook = new Book();
            newBook.setTitle(titleField.getText().trim());
            newBook.setAuthor(authorField.getText().trim());
            newBook.setIsbn(isbn);                           //già definito sopra per controllo
            newBook.setGenre(genreField.getText().trim());
            newBook.setRating(Integer.parseInt(ratingField.getText().trim()));
            newBook.setReadingStatus(statusCombo.getSelectedItem().toString());

            dispose();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Book showDialog() {
        setVisible(true);
        return newBook;
    }
}