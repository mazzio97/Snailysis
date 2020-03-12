package org.snailysis.model.utilities;

import static org.apache.commons.lang3.tuple.Pair.of;

import java.io.Serializable;
import java.util.Optional;

/**
 * Wrapper class of Apache Commons Lang's Pair, 
 * created to match Apache Commons Math's Pair methods while remaining Serializable.
 * 
 * @param <L>
 *      the generic type of the first object of the pair
 * @param <R>
 *      the generic type of the second object of the pair
 */
public final class Pair<L, R> implements Serializable {

    private static final long serialVersionUID = -1580562798771950429L;

    private final org.apache.commons.lang3.tuple.Pair<L, R> p;

    /**
     * Constructor created using the static of method of Apache Commons Lang's Pair.
     * 
     * @param first
     *          the first element of the pair
     * @param second
     *          the second element of the pair
     */
    public Pair(final L first, final R second) {
        this.p = of(first, second);
    }

    /**
     * Gets the first element of the pair, same as getKey().
     * 
     * @return
     *          the first element of the pair
     */
    public L getFirst() {
        return p.getLeft();
    }

    /**
     * Gets the second element of the pair, same as getValue().
     * 
     * @return
     *          the second element of the pair
     */
    public R getSecond() {
        return p.getRight();
    }

    /**
     * Gets the first element of the pair, same as getFirst().
     * 
     * @return
     *          the first element of the pair
     */
    public L getKey() {
        return getFirst();
    }

    /**
     * Gets the second element of the pair, same as getSecond().
     * 
     * @return
     *          the second element of the pair
     */
    public R getValue() {
        return getSecond();
    }

    /**
     * Gets a copy of this pair.
     * 
     * @return
     *          a new copy of this pair.
     */
    public Pair<L, R> getCopy() {
        return new Pair<>(getFirst(), getSecond());
    }

    @Override
    public int hashCode() {
        return p.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        return Optional.ofNullable(obj)
                       .filter(o -> Pair.class.isInstance(o))
                       .map(o -> (Pair<?, ?>) o)
                       .filter(o -> getFirst().equals(o.getFirst()))
                       .filter(o -> getSecond().equals(o.getSecond()))
                       .isPresent();
    }

    @Override
    public String toString() {
        return p.toString();
    }
}
