package org.snailysis.model.entities.function;

/**
 * Represent a bidimensional function on a Cartesian plane, with its basic operations.
 */
public interface Function2D {

    /**
     * Adds a g(x) function to the function.
     * 
     * @param gx
     *          the function to be added
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if g(x) has a different class of f(x)
     */
    Function2D add(Function2D gx);

    /**
     * Multiply the function by a g(x) function.
     * 
     * @param gx
     *          the function to be multiplied by
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if g(x) has a different class of f(x)
     */
    Function2D multiply(Function2D gx);

    /**
     * Compose the functions returning f(g(x)).
     * 
     * @param gx
     *          the function to be composed
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if g(x) has a different class of f(x)
     */
    Function2D compose(Function2D gx);

    /**
     *  Returns the same function.
     * 
     * @return
     *          the result function 
     */
    Function2D identity();

    /**
     * Translate the function on the X axis.
     * 
     * @param constant 
     *          the value of the constant
     * @return
     *          the result function
     */
    Function2D translateOnY(double constant);

    /**
     * Translate the function on the X axis.
     * 
     * @param constant
     *          the value of the constant
     * @return
     *          the result function
     */
    Function2D translateOnX(double constant);

    /**
     * Negates the function: -f(x).
     * 
     * @return
     *          the result function
     */
    Function2D opposeOnY();

    /**
     * Negates the variable: f(-x).
     * 
     * @return
     *          the result function
     */
    Function2D opposeOnX();

    /**
     * Dilates the function: kf(x).
     * 
     * @param coefficient
     *          the value of k
     * @return 
     *          the result function
     */
    Function2D dilateOnY(double coefficient);

    /**
     * Dilates the variable: f(kx).
     * 
     * @param coefficient
     *          the value of k
     * @return
     *          the result function
     */
    Function2D dilateOnX(double coefficient);

    /**
     * Gets the function multiplied n-times: [f(x)]^n.
     * 
     * @param power
     *          the value of n
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if power is not positive
     */
    Function2D powerOnY(int power);

    /**
     * Gets the variable multiplied n-times: f(x^n).
     * 
     * @param power
     *          the value of n
     * @return
     *          the result function
     * @throws
     *          IllegalArgumentException if power is not positive
     */
    Function2D powerOnX(int power);

    /**
     * Differentiates the function: df(x)/dx.
     * 
     * @return
     *          the result function
     */
    Function2D differentiate();

    /**
     * Integrates the function: Sf(x)dx.
     * 
     * @return
     *          the result function
     */
    Function2D integrate();

    /**
     * Gets a copy of the current function.
     * 
     * @return
     *          a copy of the current function
     */
    Function2D getCopy();

    /**
     * Evaluates the function value at a certain point.
     * 
     * @param x
     *          the value of the point
     * @return
     *          the value of the function
     */
    double evaluateAt(double x);
}
