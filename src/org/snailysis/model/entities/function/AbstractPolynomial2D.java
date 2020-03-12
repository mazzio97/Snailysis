package org.snailysis.model.entities.function;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.math3.fraction.Fraction;
import org.jscience.mathematics.function.Polynomial;
import org.jscience.mathematics.function.Variable;
import org.jscience.mathematics.number.Rational;

/**
 * Abstract class implementing Polynomial2D.
 */
public abstract class AbstractPolynomial2D extends AbstractFunction2D implements Polynomial2D {

    /**
     * Variable used in Polynomial Functions.
     */
    protected static final Variable<Rational> VARIABLE = new Variable.Local<>("x");

    @Override
    public final Polynomial2D add(final double coefficient, final int grade) {
        return (Polynomial2D) new BasicPolynomial2D(coefficient, grade).add(this);
    }

    @Override
    public final Polynomial2D multiply(final double coefficient, final int grade) {
        return (Polynomial2D) new BasicPolynomial2D(coefficient, grade).multiply(this);
    }

    /**
     * Protected utility function that returns a text representation of the polynomial function.
     * 
     * @param polynomial
     *          the polynomial function
     * @return
     *          the text representation
     */
    protected static String polynomialToString(final Polynomial<Rational> polynomial) {
        /*
         * First removes terms with 0 coefficient:
         * - if empty means f(x) = 0
         * - otherwise replaces "1/1", "/1" and "[]" removing spaces when necessary
         */
        return Optional.of(new LinkedList<>(Arrays.asList(polynomial.toString().split(" [+] "))))
                       .map(t -> t.removeIf(s -> s.contains("[0/")) ? t : t)
                       .filter(t -> !t.isEmpty())
                       .map(t -> t.stream()
                                  .map(s -> s.replace("1/1]x", "x"))
                                  .map(s -> s.replace("/1]", ""))
                                  .map(s -> s.replace("]", ""))
                                  .map(s -> s.replace("[", ""))
                                  .collect(Collectors.joining(" + "))
                                  .replace("+ -", "- "))
                       .orElse("0");
    }

    /**
     * Takes a double and returns a (jScience) Rational passing through the (ApacheCommonsMath) Fraction.
     * 
     * @param d
     *          the double value
     * @return
     *          the fractional value
     */
    protected static Rational getRationalFromDouble(final double d) {
        return Optional.of(new Fraction(d)).map(f -> Rational.valueOf(f.getNumerator(), f.getDenominator())).get();
    }
}
