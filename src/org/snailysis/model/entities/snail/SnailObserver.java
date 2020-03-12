package org.snailysis.model.entities.snail;

/**
 * Functional interface of a Snail Observer.
 */
@FunctionalInterface
public interface SnailObserver {

    /**
     * Enum representing the types of notification sent from the snail to the observer.
     */
    enum SnailNotificationType {
        RESTART, OPERATION_PERFORMED;
    }

    /**
     * Notifies the observer.
     * 
     * @param notificationType
     *          indicates what kind of notification did the call
     */
    void notifyObserver(SnailNotificationType notificationType);
}
