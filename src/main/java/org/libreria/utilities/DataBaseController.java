package org.libreria.utilities;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.libreria.DTO.BookUpdateDTO;
import org.libreria.DuplicateIsbnException;
import org.libreria.books.Book;
import org.libreria.libraries.Library;

import java.io.File;
import java.io.IOException;

public class DataBaseController {

    public static void saveLibrary(Library lib, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filename), lib);
    }

    public static Library loadLibrary(String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filename), Library.class);
    }

    public static void addBookToJson(Book book, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filename);

        // Se il file non esiste, crea una nuova libreria
        if (!file.exists()) {
            Library newLibrary = new Library();
            newLibrary.getBooks().add(book);
            saveLibrary(newLibrary, filename);
            return;
        }

        // Legge il file JSON esistente come JsonNode
        JsonNode rootNode = mapper.readTree(file);
        ArrayNode booksArray = (ArrayNode) rootNode.get("books");

        // Verifica se l'ISBN è già presente
        if (isIsbnDuplicate(book.getIsbn(), booksArray)) {
            throw new DuplicateIsbnException("Un libro con ISBN " + book.getIsbn() + " è già presente nella libreria.");
        }

        // Converte il nuovo libro in JsonNode
        JsonNode bookNode = mapper.valueToTree(book);

        // Aggiunge il nuovo libro all'array
        booksArray.add(bookNode);

        // Salva il file aggiornato
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
    }

    private static boolean isIsbnDuplicate(String isbn, ArrayNode booksArray) {
        for (JsonNode bookNode : booksArray) {
            if (bookNode.get("isbn").asText().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    public static void updateBook(String isbn, BookUpdateDTO updateDTO, String filename)
            throws IOException, IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(filename);

        if (!file.exists()) {
            throw new IllegalArgumentException("File della libreria non trovato");
        }

        // Legge il file JSON esistente
        JsonNode rootNode = mapper.readTree(file);
        ArrayNode booksArray = (ArrayNode) rootNode.get("books");
        boolean bookFound = false;

        // Cerca e aggiorna il libro
        for (int i = 0; i < booksArray.size(); i++) {
            JsonNode bookNode = booksArray.get(i);
            if (bookNode.get("isbn").asText().equals(isbn)) {
                ObjectNode mutableBookNode = (ObjectNode) bookNode;

                // Aggiorna solo i campi modificati
                if (updateDTO.isTitleModified()) {
                    mutableBookNode.put("title", updateDTO.getTitle());
                }
                if (updateDTO.isAuthorModified()) {
                    mutableBookNode.put("author", updateDTO.getAuthor());
                }
                if (updateDTO.isGenreModified()) {
                    mutableBookNode.put("genre", updateDTO.getGenre());
                }
                if (updateDTO.isRatingModified()) {
                    if (updateDTO.getRating() < 1 || updateDTO.getRating() > 5) {
                        throw new IllegalArgumentException("La valutazione deve essere tra 1 e 5");
                    }
                    mutableBookNode.put("rating", updateDTO.getRating());
                }
                if (updateDTO.isReadingStatusModified()) {
                    mutableBookNode.put("readingStatus", updateDTO.getReadingStatus());
                }

                bookFound = true;
                break;
            }
        }

        if (!bookFound) {
            throw new IllegalArgumentException("Libro con ISBN " + isbn + " non trovato");
        }

        // Salva le modifiche
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
    }



public static void main(String[] args) throws IOException {

//        Library myLibrary = new Library();
//
//        myLibrary = DataBaseController.loadLibrary("src/main/resources/database.json");

        Book b1 = new Book();
        b1.setTitle("Il nome della rosa");
        b1.setAuthor("Umberto Eco");
        b1.setIsbn("9788804498511");
        b1.setGenre("Giallo storico");
        b1.setRating(5);
        b1.setReadingStatus("letto");

//        myLibrary.getBooks().add(b1);

//        // Salva
//        DataBaseController.saveLibrary(myLibrary, "src/main/resources/database.json");

        // Aggiunge direttamente il libro al JSON
        addBookToJson(b1, "src/main/resources/database.json");

        // Carica
        Library loaded = DataBaseController.loadLibrary("src/main/resources/database.json");
        System.out.println("Hai " + loaded.getBooks().size() + " libri caricati.");

    }

}
