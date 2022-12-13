package com.jeasy.common.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
public final class PairKit {

    private PairKit() {
    }

    public static <U, V> List<U> extractFirst(Collection<Pair<U, V>> pairs) {
        List<U> result = new ArrayList<>(pairs.size());
        for (Pair<U, V> pair : pairs) {
            result.add(pair.getFirst());
        }
        return result;
    }

    public static <U, V> List<V> extractSecond(Collection<Pair<U, V>> pairs) {
        List<V> result = new ArrayList<>(pairs.size());
        for (Pair<U, V> pair : pairs) {
            result.add(pair.getSecond());
        }
        return result;
    }

    public static <T> Twins<List<T>> select(List<? extends T> list, Predicate<? super T> predicate) {
        List<T> positives = new ArrayList<>();
        List<T> negatives = new ArrayList<>();
        Twins<List<T>> result = new Twins<>(positives, negatives);
        for (T o : list) {
            if (predicate.evaluate(o)) {
                positives.add(o);
            } else {
                negatives.add(o);
            }
        }
        return result;
    }

    public static <T> Twins<List<T>> select(List<? extends T> list, Predicate<? super T> predicate, int limit) {
        List<T> positives = new ArrayList<>();
        List<T> negatives = new ArrayList<>();
        Twins<List<T>> result = new Twins<>(positives, negatives);

        for (T o : list) {

            if (positives.size() < limit && predicate.evaluate(o)) {

                positives.add(o);
            } else {

                negatives.add(o);
            }
        }
        return result;
    }
}
