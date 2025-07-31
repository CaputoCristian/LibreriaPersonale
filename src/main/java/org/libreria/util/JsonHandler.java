package org.libreria.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.libreria.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static List<Book> loadBooks(File file) {
        List<Book> books = new ArrayList<>();
        try {
            JsonNode root = mapper.readTree(file);
            ArrayNode booksArray = (ArrayNode) root.get("books");
            for (JsonNode bookNode : booksArray) {
                Book book = mapper.treeToValue(bookNode, Book.class);
                books.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static void saveBooks(File file, List<Book> books) {
        try {
            JsonNode root = mapper.createObjectNode().set("books", mapper.valueToTree(books));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

