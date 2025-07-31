package org.libreria.gui;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.command.AddBookCommand;
import org.libreria.command.Command;
import org.libreria.command.DeleteBookCommand;
import org.libreria.command.UpdateBookCommand;
import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI extends JFrame {

    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, searchButton, sortButton, refreshButton;

    public LibraryGUI() {
        setTitle("Gestione Libreria Personale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        bookTable = new JTable(); // inizializzazione base
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tableModel = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        BookTableModel tableModel = new BookTableModel(LibrarySingleton.getInstance().getLibrary().getBooks());
        bookTable.setModel(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Aggiungi");
        editButton = new JButton("Modifica");
        deleteButton = new JButton("Elimina");
        searchButton = new JButton("Cerca");
        sortButton = new JButton("Ordina");
        refreshButton = new JButton("Aggiorna");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(sortButton);
        buttonPanel.add(refreshButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        refreshButton.addActionListener(e -> loadBooks());

        addButton.addActionListener(e -> {
            BookFormDialog dialog = new BookFormDialog(this);
            Book book = dialog.showDialog();
            if (book != null) {
                Command command = new AddBookCommand(LibrarySingleton.getInstance().getLibrary(), book);
                command.execute();

                LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));

                loadBooks();
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                BookUpdateDTO selectedBookDTO = new BookUpdateDTO( tableModel.getBookAt(selectedRow) );
                BookFormDialog dialog = new BookFormDialog(this, selectedBookDTO);
                Book updatedBook = dialog.showDialog();
                if (updatedBook != null) {
                    Command command = new UpdateBookCommand(LibrarySingleton.getInstance().getLibrary(), selectedBookDTO);
                    command.execute();

                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
                    loadBooks();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Sei sicuro di voler eliminare il libro selezionato?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    Command command = new DeleteBookCommand(LibrarySingleton.getInstance().getLibrary(), selectedBook.getIsbn());
                    command.execute();

                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
                    loadBooks();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da eliminare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });


        loadBooks();
    }


//    private void loadBooks() {
//        List<BookUpdateDTO> myBookList = LibrarySingleton.getInstance()
//                .getLibrary()
//                .getAllBooks()
//                .stream()
//                .map(BookUpdateDTO::fromBook)
//                .toList();
//
//        BookTableModel tableModel = new BookTableModel(new ArrayList<>(myBookList));
//        bookTable.setModel(tableModel);
//    }

    private void loadBooks() {
        tableModel.setRowCount(0); // pulisce la tabella
        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                    book.getTitle(),
                    book.getAuthor(),
                    book.getIsbn(),
                    book.getGenre(),
                    book.getRating(),
                    book.getReadingStatus()
            });
        }

        BookTableModel tableModel = new BookTableModel(new ArrayList<>(LibrarySingleton.getInstance().getLibrary().getBooks()));
        bookTable.setModel(tableModel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);
        });
    }
}