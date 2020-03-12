package org.snailysis.model.utilities.exceptions;

import java.util.Optional;

/**
 * Utility class with static methods to throw exceptions and manage checkings.
 * This could be used into the entire project by anyone. 
 */
public final class ExceptionUtilities {

    private ExceptionUtilities() { }

    /**
     * Throws the given exception if the condition is true.
     * 
     * @param condition
     *          the condition to be verified
     * @param exception
     *          the exception to be thrown
     */
    public static void throwExceptionIf(final boolean condition, final RuntimeException exception) {
        Optional.of(!condition).filter(Boolean::booleanValue).orElseThrow(() -> exception);
    }

    /**
     * Checks if an argument is not zero and raise an IllegalArgumentException exception if not.
     * 
     * @param argument
     *          the value to be checked
     * @param message
     *          the message to be used in the exception
     */
    public static void checkNotZero(final Number argument, final String message) {
        throwExceptionIf(argument.equals(0), new IllegalArgumentException(message));
    }

    /**
     * Checks if an argument is positive and raise an IllegalArgumentException exception if not.
     * 
     * @param argument
     *          the value to be checked
     * @param message
     *          the message to be used in the exception
     */
    public static void checkPositive(final Number argument, final String message) {
        throwExceptionIf(argument.doubleValue() <= 0, new IllegalArgumentException(message));
    }

    /**
     * Checks if an argument is instance of a given clas and raise an IllegalArgumentException exception if not.
     * 
     * @param c
     *          the class to be instance of
     * @param argument 
     *          the object to be checked
     * @param message
     *          the message to be used in the exception
     */
    public static void checkInstanceOf(final Class<?> c, final Object argument, final String message) {
        throwExceptionIf(!c.isInstance(argument), new IllegalArgumentException(message));
    }

    /**
     * Checks if an object is not null and raise an IllegalArgumentException exception if it is.
     * 
     * @param object 
     *          the object to be checked
     * @param message
     *          the message to be used in the exception
     */
    public static void checkNotNull(final Object object, final String message) {
        throwExceptionIf(object == null, new IllegalArgumentException(message));
    }
}
