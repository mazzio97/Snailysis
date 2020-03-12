package org.snailysis.scenes;

import org.snailysis.scenes.entities.snail.SnailDrawer;

import javafx.scene.canvas.Canvas;

/**
 * Interface of the View part of MVC architecture pattern.
 */
public interface View {

    /**
     * Gets the class responsible of drawing the snail.
     * 
     * @return
     *          the snail drawer
     */
    SnailDrawer getSnailDrawer();

    /**
     * Setter for the canvas containing the snail.
     * 
     * @param c
     *      canvas contains snail
     */
    void setCanvas(Canvas c);

    /**
     * Getter for the canvas containing the snail.
     * 
     * @return
     *      canvas contains snail
     */
    Canvas getCanvas();

}
