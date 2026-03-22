package com.lenewblack.wholesale.pagination;

import com.lenewblack.wholesale.model.ResultSet;

import java.util.Iterator;
import java.util.function.Function;

/**
 * An {@link Iterable} that starts a fresh {@link PageIterator} traversal from page 1
 * each time {@link #iterator()} is called — consistent with the Java {@link Iterable} contract.
 *
 * <p>Usage:
 * <pre>{@code
 * for (Product p : client.products().paginate(params)) {
 *     process(p);
 * }
 *
 * // Stream-compatible:
 * StreamSupport.stream(client.products().paginate(params).spliterator(), false)
 *     .filter(p -> p.getName() != null)
 *     .forEach(this::process);
 * }</pre>
 *
 * @param <T> the item type
 */
public final class PageIterable<T> implements Iterable<T> {

    private final Function<Integer, ResultSet<T>> pageFetcher;

    public PageIterable(Function<Integer, ResultSet<T>> pageFetcher) {
        this.pageFetcher = pageFetcher;
    }

    @Override
    public Iterator<T> iterator() {
        return new PageIterator<>(pageFetcher);
    }
}
