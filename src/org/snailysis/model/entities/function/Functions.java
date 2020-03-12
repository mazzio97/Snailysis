package org.snailysis.model.entities.function;

/**
 * Static Factory used to create basic functions.
 */
public final class Functions {

    // this is a utility class, so it should not be istantiated
    private Functions() { }

    /**
     * Creates a constant function: f(x) = c.
     * @param c
     *          the value of the function
     * @return
     *          the function created
     */
    public static Function2D constant(final double c) {
        return new Constant(c);
    }

    /**
     * Creates the function f(x) = ax^b.
     * 
     * @param coefficient
     *          the value of a
     * @param grade
     *          the value of b
     * @return
     *          the function created
     * @throws
     *          IllegalArgumentException if coefficient is zero
     *          IllegalArgumentException if grade is not positive
     */
    public static Polynomial2D polynomial(final double coefficient, final int grade) {
        return new BasicPolynomial2D(coefficient, grade);
    }
}
