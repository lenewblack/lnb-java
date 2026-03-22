package com.lenewblack.wholesale.pagination;

import com.lenewblack.wholesale.model.ResultSet;

import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.function.Function;

/**
 * Lazy page-by-page iterator. Fetches the next page only when the current buffer
 * is exhausted, mimicking PHP generator-based pagination.
 *
 * @param <T> the item type
 */
public final class PageIterator<T> implements Iterator<T> {

    private final Function<Integer, ResultSet<T>> pageFetcher;
    private int currentPage = 1;
    private final Queue<T> buffer = new ArrayDeque<>();
    private boolean hasMore = true;
    private boolean initialized = false;

    /**
     * @param pageFetcher a function that accepts a 1-based page number and returns a {@link ResultSet}
     */
    public PageIterator(Function<Integer, ResultSet<T>> pageFetcher) {
        this.pageFetcher = pageFetcher;
    }

    @Override
    public boolean hasNext() {
        if (!buffer.isEmpty()) {
            return true;
        }
        if (!hasMore && initialized) {
            return false;
        }
        // Fetch the next page
        ResultSet<T> page = pageFetcher.apply(currentPage++);
        initialized = true;
        if (page.getData() != null) {
            buffer.addAll(page.getData());
        }
        hasMore = page.hasMore();
        return !buffer.isEmpty();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more items");
        }
        return buffer.poll();
    }
}
