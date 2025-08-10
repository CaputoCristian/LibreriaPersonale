package org.libreria.model;

public class SearchFilter {
    private String searchTerm;
    private boolean searchByTitle; // true = titolo, false = autore
    private String readingStatusFilter; // "letto", "in lettura", "da leggere", oppure null
    private Integer minRating; // può essere null

    public SearchFilter(String searchTerm, boolean searchByTitle, String readingStatusFilter, Integer minRating) {
        this.searchTerm = searchTerm;
        this.searchByTitle = searchByTitle;
        this.readingStatusFilter = readingStatusFilter;
        this.minRating = minRating;
    }
    public String getSearchTerm() {
        return searchTerm;
    }

    public boolean isSearchByTitle() {
        return searchByTitle;
    }

    public String getReadingStatusFilter() {
        return readingStatusFilter;
    }

    public Integer getMinRating() {
        return minRating;
    }
}