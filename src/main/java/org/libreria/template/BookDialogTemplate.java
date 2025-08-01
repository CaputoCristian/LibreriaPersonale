package org.libreria.template;

import javax.swing.*;
import java.awt.*;

public abstract class BookDialogTemplate extends JDialog {
    protected JTextField titleField, authorField, isbnField, genreField, ratingField, readingStatusField;
    protected JButton confirmButton, cancelButton;

    public BookDialogTemplate(JFrame parent, String title) {
        super(parent, true);
        setTitle(title);
        setSize(350, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Titolo:")); titleField = new JTextField(); inputPanel.add(titleField);
        inputPanel.add(new JLabel("Autore:")); authorField = new JTextField(); inputPanel.add(authorField);
        inputPanel.add(new JLabel("ISBN:")); isbnField = new JTextField(); inputPanel.add(isbnField);
        inputPanel.add(new JLabel("Genere:")); genreField = new JTextField(); inputPanel.add(genreField);
        inputPanel.add(new JLabel("Rating (0-5):")); ratingField = new JTextField(); inputPanel.add(ratingField);
        inputPanel.add(new JLabel("Stato:")); readingStatusField = new JTextField(); inputPanel.add(readingStatusField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("Conferma");
        cancelButton = new JButton("Annulla");
        buttonPanel.add(confirmButton); buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        confirmButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> dispose());
    }

    protected boolean validateCommonFields() {
        // Campo vuoto?
        if (titleField.getText().isEmpty() || authorField.getText().isEmpty() || isbnField.getText().isEmpty()
                || genreField.getText().isEmpty() || ratingField.getText().isEmpty()
                || readingStatusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tutti i campi sono obbligatori.");
            return false;
        }

        // ISBN valido
        if (!isbnField.getText().matches("^\\d{10}$|^\\d{13}$|^\\d{9}X$")) {
            JOptionPane.showMessageDialog(this,
                    "L'ISBN deve contenere 10 o 13 cifre (senza trattini).",
                    "ISBN non valido", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Rating valido
        try {
            int rating = Integer.parseInt(ratingField.getText().trim());
            if (rating < 0 || rating > 5) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Il rating deve essere un numero intero tra 0 e 5.");
            return false;
        }

        return true;
    }

    // Metodo da definire nelle sottoclassi
    protected abstract void onConfirm();
}
