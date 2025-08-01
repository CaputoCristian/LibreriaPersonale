package org.libreria.gui;

import org.libreria.DTO.BookUpdateDTO;
import org.libreria.command.AddBookCommand;
import org.libreria.command.Command;
import org.libreria.command.DeleteBookCommand;
import org.libreria.command.UpdateBookCommand;
import org.libreria.model.Book;
import org.libreria.singleton.LibrarySingleton;
import org.libreria.template.AddBookDialog;
import org.libreria.template.UpdateBookDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LibraryGUI extends JFrame {

    private JTable bookTable;
//    private DefaultTableModel tableModel;
    private BookTableModel tableModel;
    private JButton addButton, editButton, deleteButton, searchButton, sortButton, refreshButton;

    public LibraryGUI() {
        setTitle("Gestione Libreria Personale");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        LibrarySingleton.getInstance().loadBooksFromJson(new File("database.json"));
        System.out.println("Libri caricati: " + LibrarySingleton.getInstance().getLibrary().getBooks().size());

        bookTable = new JTable(); // inizializzazione base
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

//        tableModel = new DefaultTableModel(new Object[]{"Titolo", "Autore", "ISBN", "Genere", "Valutazione", "Stato"}, 0);
        tableModel = new BookTableModel(LibrarySingleton.getInstance().getLibrary().getBooks());
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

//        addButton.addActionListener(e -> {
//            BookFormDialog dialog = new BookFormDialog(this);
//            BookUpdateDTO book = dialog.showDialog();
//            if (book != null) {
//                Command command = new AddBookCommand(LibrarySingleton.getInstance().getLibrary(), book);
//                command.execute();
//
//                LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//
//                loadBooks();
//            }
//        });
//
//        editButton.addActionListener(e -> {
//            int selectedRow = bookTable.getSelectedRow();
//            if (selectedRow >= 0) {
//                Book selectedBook = tableModel.getBookAt(selectedRow);
//                BookFormDialog dialog = new BookFormDialog(this, selectedBook);
//                BookUpdateDTO updatedBook = dialog.showDialog();
//                if (updatedBook != null) {
//                    Command command = new UpdateBookCommand(LibrarySingleton.getInstance().getLibrary(), updatedBook);
//                    command.execute();
//
//                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                    loadBooks();
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
//            }
//        });

        // Aggiunta libro
        addButton.addActionListener(e -> {
            AddBookDialog dialog = new AddBookDialog(this);
            Book newBook = dialog.showDialog();
            if (newBook != null) {
                Command command = new AddBookCommand(LibrarySingleton.getInstance().getLibrary(), newBook);
                command.execute();

                LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
                loadBooks();
            }
        });

        // Modifica libro
        editButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                UpdateBookDialog dialog = new UpdateBookDialog(this, selectedBook);
                BookUpdateDTO updatedBook = dialog.showDialog();
                if (updatedBook != null) {
                    Command command = new UpdateBookCommand(LibrarySingleton.getInstance().getLibrary(), updatedBook);
                    command.execute();

                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
                    loadBooks();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Seleziona un libro da modificare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
            }
        });

//        deleteButton.addActionListener(e -> {
//            int selectedRow = bookTable.getSelectedRow();
//            if (selectedRow >= 0) {
//                Book selectedBook = tableModel.getBookAt(selectedRow);
//                int confirm = JOptionPane.showConfirmDialog(this,
//                        "Sei sicuro di voler eliminare il libro selezionato?",
//                        "Conferma eliminazione",
//                        JOptionPane.YES_NO_OPTION);
//
//                if (confirm == JOptionPane.YES_OPTION) {
//                    Command command = new DeleteBookCommand(LibrarySingleton.getInstance().getLibrary(), selectedBook.getIsbn());
//                    command.execute();
//
//                    LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));
//                    loadBooks();
//                }
//            } else {
//                JOptionPane.showMessageDialog(this, "Seleziona un libro da eliminare.", "Nessuna selezione", JOptionPane.WARNING_MESSAGE);
//            }
//        });

        deleteButton.addActionListener(e -> {
            int selectedRow = bookTable.getSelectedRow();
            if (selectedRow >= 0) {
                Book selectedBook = tableModel.getBookAt(selectedRow);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Sei sicuro di voler eliminare il libro selezionato?",
                        "Conferma eliminazione",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    try {
                        Command command = new DeleteBookCommand(LibrarySingleton.getInstance().getLibrary(),
                                selectedBook.getIsbn());
                        command.execute();

                        // Rimuovi prima dal modello
                        tableModel.removeBookAt(selectedRow);

                        // Poi salva su file
                        LibrarySingleton.getInstance().saveBooksToJson(new File("database.json"));

                        // Aggiungi un log per debug
                        System.out.println("Libro eliminato: " + selectedBook.getTitle());
                        System.out.println("Libri rimanenti: " +
                                LibrarySingleton.getInstance().getLibrary().getBooks().size());

                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this,
                                "Errore durante l'eliminazione: " + ex.getMessage(),
                                "Errore",
                                JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Seleziona un libro da eliminare.",
                        "Nessuna selezione",
                        JOptionPane.WARNING_MESSAGE);
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

//    private void loadBooks() {
//        tableModel.setRowCount(0); // pulisce la tabella
//        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
//        for (Book book : books) {
//            tableModel.addRow(new Object[]{
//                    book.getTitle(),
//                    book.getAuthor(),
//                    book.getIsbn(),
//                    book.getGenre(),
//                    book.getRating(),
//                    book.getReadingStatus()
//            });
//        }
//
//        BookTableModel tableModel = new BookTableModel(new ArrayList<>(LibrarySingleton.getInstance().getLibrary().getBooks()));
//        bookTable.setModel(tableModel);
//    }

    private void loadBooks() {
        List<Book> books = LibrarySingleton.getInstance().getLibrary().getBooks();
        tableModel.setBooks(books);

        System.out.println("Aggiornamento tabella con " + books.size() + " libri");

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LibraryGUI().setVisible(true);

        });
    }
}