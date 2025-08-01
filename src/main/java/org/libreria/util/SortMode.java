package org.libreria.util;

public enum SortMode {
    BY_INSERTION, // ordine naturale del JSON
    BY_TITLE,
    BY_AUTHOR;

    public SortMode next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}