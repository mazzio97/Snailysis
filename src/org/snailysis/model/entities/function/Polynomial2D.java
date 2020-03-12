package org.snailysis.model.entities.function;

/**
 * Represent a bidimensional polynomial function on a Cartesian plane, with its basic operations.
 */
public interface Polynomial2D extends Function2D {

    /**
     * Adds a term to the function.
     * 
     * @param coefficient
     *          the coefficient of the term
     * @param grade
     *          the grade of the term
     * @return 
     *          the result function
     * @throws
     *          IllegalArgumentException if coefficient is zero
     *          IllegalArgumentException if grade is not positive
     */
    Polynomial2D add(double coefficient, int grade);

    /**
     * Multiplies the function by a term.
     * 
     * @param coefficient
     *          the coefficient of the term
     * @param grade
     *          the grade of the term
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if coefficient is zero
     *          IllegalArgumentException if grade is not positive
     */
    Polynomial2D multiply(double coefficient, int grade);
}
