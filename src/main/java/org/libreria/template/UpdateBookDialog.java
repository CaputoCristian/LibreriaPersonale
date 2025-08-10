package org.libreria.template;

import org.libreria.model.Book;

import javax.swing.*;

public class UpdateBookDialog extends BookDialogTemplate {

    private Book updatedBook;

    public UpdateBookDialog(JFrame parent, Book bookToEdit) {
        super(parent, "Modifica Libro");

        // Precompilazione con dati esistenti
        titleField.setText(bookToEdit.getTitle());
        authorField.setText(bookToEdit.getAuthor());
        isbnField.setText(bookToEdit.getIsbn());
        genreField.setText(bookToEdit.getGenre());
        ratingField.setText(String.valueOf(bookToEdit.getRating()));
        statusCombo.setSelectedItem(bookToEdit.getReadingStatus());

//        isbnField.setEnabled(false); // non modificabile
    }

    @Override
    protected void onConfirm() {
        if (!validateFields()) return;

        updatedBook = new Book(
                titleField.getText().trim(),
                authorField.getText().trim(),
                isbnField.getText().trim(),
                genreField.getText().trim(),
                Integer.parseInt(ratingField.getText().trim()),
                statusCombo.getSelectedItem().toString()
                );

        dispose();
    }

    public Book showDialog() {
        setVisible(true);
        return updatedBook;
    }
}
