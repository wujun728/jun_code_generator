package com.jeasy.common.collection;

import com.jeasy.common.Func;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @param <T>
 * @author taomk
 * 按Pair的第一个元素的比较器
 */
public class PairFirstElemComparator<T extends Comparable<T>> implements Comparator<Pair<T, ?>>, Serializable {

    private static final long serialVersionUID = 4645527652929432522L;

    @Override
    public int compare(Pair<T, ?> o1, Pair<T, ?> o2) {
        if (Func.isEmpty(o1) && Func.isEmpty(o2)) {
            return 0;
        } else if (o1 == null) {
            return Integer.compare(0, o2.getFirst().compareTo(null));
        } else if (o2 == null) {
            return o1.getFirst().compareTo(null);
        } else {
            return o1.getFirst().compareTo(o2.getFirst());
        }
    }

    public static <T1> Comparator<Pair<T1, ?>> getInstance(final Comparator<T1> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("comparator can not be null!");
        }
        return new Comparator<Pair<T1, ?>>() {
            @Override
            public int compare(Pair<T1, ?> o1, Pair<T1, ?> o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                } else if (o1 == null) {
                    return comparator.compare(null, o2.getFirst());
                } else if (o2 == null) {
                    return comparator.compare(o1.getFirst(), null);
                } else {
                    return comparator.compare(o1.getFirst(), o2.getFirst());
                }
            }
        };
    }

}
