package org.snailysis.test.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;
import org.snailysis.model.entities.function.Function2D;
import org.snailysis.model.entities.function.Functions;
import org.snailysis.model.entities.function.Polynomial2D;

class TestFunction2D {

    private static final String FUNCTION_ERROR = "Function Error";
    private static final String EVALUATION_ERROR = "Evaluation Error";
    private static final String EXCEPTION_ERROR = "Exception Error";

    private static final double ERROR = 0.01;
    private static final String NOT_POSITIVE_GRADE_TEXT = "Not Positive Values are not accepted";

    @Test
    public void basicTest() {
        // CREATING functions -> f(x) = 0 && g(x) = 3x² -5x + 2
        final Function2D gx = Functions.polynomial(3, 2).add(1, 1).translateOnX(-1);
        Function2D fx = Functions.constant(3.0 / 2);
        assertEquals(FUNCTION_ERROR, "3x² - 5x + 2", gx.toString());
        assertEquals(FUNCTION_ERROR, "3/2", fx.toString());
        assertEquals(EVALUATION_ERROR, 3.0 / 2, fx.evaluateAt(1), ERROR);
        // ADDING -0.5 -> f(x) = 1
        fx = fx.translateOnY(-0.5);
        assertEquals(FUNCTION_ERROR, "1", fx.toString());
        assertEquals(EVALUATION_ERROR, 1, fx.evaluateAt(1), ERROR);
        // ADDING 2x -> f(x) = 2x+1
        fx = ((Polynomial2D) fx).add(2, 1);
        assertEquals(FUNCTION_ERROR, "2x + 1", fx.toString());
        assertEquals(EVALUATION_ERROR, -1, fx.evaluateAt(-1), ERROR);
        assertEquals(EVALUATION_ERROR, 1, fx.evaluateAt(0), ERROR);
        assertEquals(EVALUATION_ERROR, 3, fx.evaluateAt(1), ERROR);
        // TRY ADDING x^0 and 3x^-1 -> Exception
        try {
            ((Polynomial2D) fx).add(1, 0);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_GRADE_TEXT, e.getMessage());
        }
        try {
            ((Polynomial2D) fx).add(3, -1);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_GRADE_TEXT, e.getMessage());
        }
        // ADDING g(x) -> f(x) = 3x² - 3x + 3
        fx = fx.add(gx);
        assertEquals(FUNCTION_ERROR, "3x² - 3x + 3", fx.toString());
        assertEquals(EVALUATION_ERROR, 3, fx.evaluateAt(0), ERROR);
        // ADDING x³ -> f(x) = x³ + 3x² - 3x + 3
        fx = ((Polynomial2D) fx).add(1, 3);
        assertEquals(FUNCTION_ERROR, "x³ + 3x² - 3x + 3", fx.toString());
        assertEquals(EVALUATION_ERROR, 3, fx.evaluateAt(0), ERROR);
        assertEquals(EVALUATION_ERROR, 4, fx.evaluateAt(1), ERROR);
    }

    @Test
    public void opposeAndDilateTest() {
        // CREATING function -> f(x) = x + 1
        Function2D fx = Functions.constant(1).add(Functions.polynomial(1, 1));
        assertEquals(FUNCTION_ERROR, "x + 1", fx.toString());
        // DILATING Y of 2 -> f(x) = 2x + 2
        fx = fx.dilateOnY(2);
        assertEquals(FUNCTION_ERROR, "2x + 2", fx.toString());
        // DILATING X of 1/2 -> f(x) = x + 2
        fx = fx.dilateOnX(0.5);
        assertEquals(FUNCTION_ERROR, "x + 2", fx.toString());
        // DILATING Y of 1/2 -> f(x) = 1/2x + 1
        fx = fx.dilateOnY(0.5);
        assertEquals(FUNCTION_ERROR, "1/2x + 1", fx.toString());
        // DILATING X of 3 -> f(x) = 3/2x + 1
        fx = fx.dilateOnX(3);
        assertEquals(FUNCTION_ERROR, "3/2x + 1", fx.toString());
    }

    @Test
    public void multiplyAndPowerTest() {
        // CREATING functions -> f(x) = 2 && g(x) = x + 2
        final Function2D gx = Functions.polynomial(1, 1).translateOnY(2);
        Function2D fx = Functions.constant(2);
        // MULTIPLYING by 1/2x then ADDING 1 -> f(x) = x
        fx = ((Polynomial2D) fx).multiply(0.5, 1);
        assertEquals(FUNCTION_ERROR, "x", fx.toString());
        // MULTIPLYING by g(x) -> f(x) = x² + 2x
        fx = fx.multiply(gx);
        assertEquals(FUNCTION_ERROR, "x² + 2x", fx.toString());
        // POWERING Y by 2 -> f(x) = x4 + 4x³ + 4x²
        fx = fx.powerOnY(2);
        assertEquals(FUNCTION_ERROR, "x4 + 4x³ + 4x²", fx.toString());
        // POWERING X by 3 -> f(x) = x12 + 4x9 + 4x6
        fx = fx.powerOnX(3);
        assertEquals(FUNCTION_ERROR, "x12 + 4x9 + 4x6", fx.toString());
        // TRY POWERING by -1 on X and Y -> Exception
        try {
            fx.powerOnX(-1);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_GRADE_TEXT, e.getMessage());
        }
        try {
            fx.powerOnY(-1);
            fail(EXCEPTION_ERROR);
        } catch (IllegalArgumentException e) {
            assertEquals(EXCEPTION_ERROR, NOT_POSITIVE_GRADE_TEXT, e.getMessage());
        }
    }

    @Test
    public void differentiationAndIntegrationTest() {
        // CREATING function -> f(x) = 3x² + 2
        Function2D fx = Functions.polynomial(3, 2);
        fx = fx.translateOnY(2);
        // DIFFERENTIATING -> f(x) = 6x
        fx = fx.differentiate();
        assertEquals(FUNCTION_ERROR, "6x", fx.toString());
        // DIFFERENTIATING -> f(x) = 6
        fx = fx.differentiate();
        assertEquals(FUNCTION_ERROR, "6", fx.toString());
        // DIFFERENTIATING -> f(x) = 0
        fx = fx.differentiate();
        assertEquals(FUNCTION_ERROR, "0", fx.toString());
        // INTEGRATING -> f(x) = 1
        fx = fx.integrate();
        assertEquals(FUNCTION_ERROR, "0", fx.toString());
        // FUNCTION -> f(x) = x
        fx = Functions.polynomial(1, 1);
        assertEquals(FUNCTION_ERROR, "x", fx.toString());
        // ADDING 3x² + 3x + 2 -> f(x) = 3x² + 4x + 2
        fx = fx.add(Functions.polynomial(3, 2).add(3, 1).translateOnY(2));
        assertEquals(FUNCTION_ERROR, "3x² + 4x + 2", fx.toString());
        // INTEGRATING -> f(x) = x³ + 3x² + 2x
        fx = fx.integrate();
        assertEquals(FUNCTION_ERROR, "x³ + 2x² + 2x", fx.toString());
        // INTEGRATING -> f(x) = 1/4x4 + x³ + 2x²
        fx = fx.integrate();
        assertEquals(FUNCTION_ERROR, "1/4x4 + 2/3x³ + x²", fx.toString());
    }
}
