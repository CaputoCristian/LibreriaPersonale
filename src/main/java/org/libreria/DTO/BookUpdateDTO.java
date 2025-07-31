package org.libreria.DTO;

import org.libreria.model.AbstractBook;
import org.libreria.model.Book;

public class BookUpdateDTO extends AbstractBook {
    private String title;
    private String author;
    private String genre;
    private Integer rating;
    private String readingStatus;
    private boolean titleModified;
    private boolean authorModified;
    private boolean genreModified;
    private boolean ratingModified;
    private boolean readingStatusModified;

    public BookUpdateDTO(Book book) {
        title = book.getTitle();
        author = book.getAuthor();
        genre = book.getGenre();
        rating = book.getRating();
        readingStatus = book.getReadingStatus();
        titleModified = true;
        authorModified = true;
        genreModified = true;
        ratingModified = true;
        readingStatusModified = true;
    }

    public BookUpdateDTO() {
    }

    public static class Builder {
        private final BookUpdateDTO dto;

        public Builder() {
            dto = new BookUpdateDTO();
        }

        public Builder title(String title) {
            dto.title = title;
            dto.titleModified = true;
            return this;
        }

        public Builder author(String author) {
            dto.author = author;
            dto.authorModified = true;
            return this;
        }

        public Builder genre(String genre) {
            dto.genre = genre;
            dto.genreModified = true;
            return this;
        }

        public Builder rating(Integer rating) {
            dto.rating = rating;
            dto.ratingModified = true;
            return this;
        }

        public Builder readingStatus(String readingStatus) {
            dto.readingStatus = readingStatus;
            dto.readingStatusModified = true;
            return this;
        }

        public BookUpdateDTO build() {
            return dto;
        }
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public Integer getRating() { return rating; }
    public String getReadingStatus() { return readingStatus; }

    // Modificato checkers
    public boolean isTitleModified() { return titleModified; }
    public boolean isAuthorModified() { return authorModified; }
    public boolean isGenreModified() { return genreModified; }
    public boolean isRatingModified() { return ratingModified; }
    public boolean isReadingStatusModified() { return readingStatusModified; }
}
