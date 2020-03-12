package org.snailysis.model.levels.builder;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.snailysis.model.utilities.Pair;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.entities.snail.Operation;
import org.snailysis.model.entities.wall.Wall;

/**
 * An interface describing a generic builder responsible for adding specific elements in the level.
 * Each LevelBuilder implementing class must specify the type of objects which needs to be added in the level
 * and a Buildable implementing class representing the next LevelBuilder in the build process 
 * or the ready to be played Level if all the required properties are set.
 *
 * @param <X>
 *              the type of objects you want to populate the level with
 * @param <Y>
 *              the type of object this builder aims to build, can be either a level or another level builder
 */
public interface LevelBuilder<X, Y extends Buildable> extends Buildable {

    /**
     * Adds an element of type X in the builder.
     * 
     * @param elem
     *          the object to add in the level
     * @throws
     *          IllegalStateException if already built or the element can't be added
     */
    void add(X elem);

    /**
     * Adds a collection of elements of type X in the builder.
     * 
     * @param elems
     *          an already existing collection of objects to add in the level
     * @throws
     *          IllegalStateException if already built or the element can't be added
     */
    void addAll(Collection<X> elems);

    /**
     * Removes an element of type X from this builder.
     * 
     * @param elem
     *          the element to remove from the level
     */
    void remove(X elem);

    /**
     * Removes a collection of elements of type X from the builder.
     * 
     * @param elems
     *            the collection of elements to remove from the level
     */
    void removeAll(Collection<X> elems);

    /**
     * Removes all the elements present in this builder.
     */
    void clear();

    /**
     * Gets a stream of the elements actually in the level mapped from X to ? 
     * where the wildcard stays for the type of objects which this builder will set in the level.
     * 
     * @return
     *          a stream containing the processed elements inserted in this builder until now
     */
    Stream<?> getElems();

    /**
     * Gets the next builder or the finished level if this is the last builder.
     * 
     * @return 
     *          a new builder for the level or the completed level
     */
    Y build();

    /**
     * Interface representing a reference to all the objects 
     * added in the level in creation by the previous builders.
     */
    interface Properties {

        /**
         * Sets the initial trajectory of the snail in the level to be built.
         * 
         * @param trajectory
         *          the function describing trajectory of the snail at the beginning of the level to be built
         * @return
         *          this
         */
        LevelBuilder.Properties setInitTrajectory(InitialTrajectory trajectory);

        /**
         * Gets the initial trajectory of the snail in the level to be built.
         * 
         * @return
         *          the function describing trajectory of the snail at the beginning of the level to be built
         * @throws
         *          IllegalStateException if not already set because the builder 
         *                                responsible for this has not been reached yet
         */
        InitialTrajectory getInitTrajectory();

        /**
         * Sets all the available operations which can be performed on the snail in the level to be built.
         * 
         * @param operations
         *          the set of available operations in the level to be built
         * @return
         *          this
         */
        LevelBuilder.Properties setAvailableOperations(Set<Operation> operations);

        /**
         * Gets all the available operations which can be performed on the snail in the level to be built.
         * 
         * @return
         *          the set of available operations in the level to be built
         * @throws
         *          IllegalStateException if not already set because the builder 
         *                                responsible for this has not been reached yet
         */
        Set<Operation> getAvailableOperations();

        /**
         * Sets a solution for the level to be built.
         * 
         * @param solution
         *          the map of (x, y) -> operation entries to achieve the solution in the level to be built
         * @return
         *          this
         */
        LevelBuilder.Properties setSolutionSteps(Map<Pair<Double, Double>, Operation> solution);

        /**
         * Gets a solution for the level to be built.
         * 
         * @return
         *          a map of (x, y) -> operation entries corresponding to the solution of the level to be built
         * @throws
         *          IllegalStateException if not already set because the builder 
         *                                responsible for this has not been reached yet
         */
        Map<Pair<Double, Double>, Operation> getSolutionSteps();

        /**
         * Sets all the walls to pass to finish the level to be built.
         * 
         * @param obstacles
         *          the set of walls presents in the level to be built
         * @return
         *          this
         */
        LevelBuilder.Properties setObstacles(Set<Wall> obstacles);

        /**
         * Gets all the walls of the level to be built.
         * 
         * @return
         *          a set with all the walls in the level to be built
         * @throws
         *          IllegalStateException if not already set because the builder 
         *                                responsible for this has not been reached yet
         */
        Set<Wall> getObstacles();

    }

}
