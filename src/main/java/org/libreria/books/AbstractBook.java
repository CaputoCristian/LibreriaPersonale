package org.libreria.books;

public abstract class AbstractBook {
    protected String title;
    protected String author;
    protected String isbn;
    protected String genre;
    protected int rating; // 1-5
    protected String readingStatus; // "letto", "in lettura", "da leggere"

    public AbstractBook() {
    }

    public AbstractBook(String title, String author, String isbn, String genre, int rating, String readingStatus) {
        this.title = title;
        this.author = author;
        setIsbn(isbn);           // uso del setter per validazione campo
        this.genre = genre;
        setRating(rating);       // uso del setter per validazione campo
        this.readingStatus = readingStatus;
    }

    // Getter e Setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        if (isbn == null || !isbn.matches("^(\\d{10}|\\d{13}|(\\d{1,5}-\\d{1,7}-\\d{1,7}-[\\dX]))$")) {
            throw new IllegalArgumentException("ISBN non valido. Deve contenere 10 o 13 cifre, con al più un trattino.");
        }
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("La valutazione deve essere contenuta tra 1 e 5.");
        }
        this.rating = rating;
    }

    public String getReadingStatus() {
        return readingStatus;
    }

    public void setReadingStatus(String readingStatus) {
        this.readingStatus = readingStatus;
    }

}