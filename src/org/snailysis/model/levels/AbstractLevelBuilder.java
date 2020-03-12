package org.snailysis.model.levels;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.snailysis.model.levels.builder.Buildable;
import org.snailysis.model.levels.builder.LevelBuilder;
import org.snailysis.model.utilities.exceptions.ExceptionUtilities;

/**
 * Abstract class defining the common behaviour of every LevelBuilder.
 * 
 * @param <X>
 *              the type of objects you want to populate the level with
 * @param <Y>
 *              the type of object this builder aims to build, can be either a level or another level builder
 */
public abstract class AbstractLevelBuilder<X, Y extends Buildable> implements LevelBuilder<X, Y> {

    private final LevelBuilder.Properties lvlProps;
    private final Set<X> elemsInLevel = new LinkedHashSet<>();
    private boolean built;

    /**
     * Constructor of the AbstractLevelBuilder.
     * Each class extending this, when created, must know the properties added in the level by the previous builders
     * since them can be useful to add its own elements.
     * 
     * @param props
     *          the properties of the level to build
     */
    public AbstractLevelBuilder(final LevelBuilder.Properties props) {
        this.lvlProps = props;
    }

    @Override
    public final void add(final X elem) {
        ExceptionUtilities.throwExceptionIf(built, new IllegalStateException("Already built, cannot add an element"));
        ExceptionUtilities.throwExceptionIf(!canBeAdded(elem), 
                                            new IllegalStateException("Element " + elem.toString() + " cannot be added in the builder"));
        elemsInLevel.add(elem);
    }

    @Override
    public final void addAll(final Collection<X> elems) {
        elems.forEach(this::add);
    }

    @Override
    public final void remove(final X key) {
        ExceptionUtilities.throwExceptionIf(built, new IllegalStateException("Already built, cannot remove an element"));
        elemsInLevel.remove(key);
    }

    @Override
    public final void removeAll(final Collection<X> keys) {
        keys.forEach(this::remove);
    }

    @Override
    public final void clear() {
        ExceptionUtilities.throwExceptionIf(built, new IllegalStateException("Already built, cannot clear"));
        elemsInLevel.clear();
    }

    @Override
    public final Y build() {
        ExceptionUtilities.throwExceptionIf(built, new IllegalStateException("Already built, cannot rebuild"));
        built = true;
        return nextBuildable();
    }

    @Override
    public abstract Stream<?> getElems();

    /**
     * Gets a set of elements of type X not necessarily ready to be added in the level.
     * 
     * @return
     *          the set of elements in this builder until now
     */
    protected final Set<X> getRawElems() {
        return Collections.unmodifiableSet(this.elemsInLevel);
    }

    /**
     * Gets the properties of the level at this point of the building process.
     * 
     * @return
     *          the properties of the level until now
     */
    protected final LevelBuilder.Properties getProperties() {
        return this.lvlProps;
    }

    /**
     * Condition to verify before an element can be added in this builder.
     * 
     * @param elem
     *          the element to check
     * @return
     *          whenever the element can be added in this builder or not
     */
    protected abstract boolean canBeAdded(X elem);

    /**
     * Create the next LevelBuilder or the completed level.
     * 
     * @return
     *          the buildable implementing object specified
     */
    protected abstract Y nextBuildable();

}
