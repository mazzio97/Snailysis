package org.snailysis.scenes.entities.wall;

import org.snailysis.model.entities.wall.Wall;

/**
 * Interface corresponding to the view part of the Wall.
 */
public interface WallView {

    /**
     * Gets the wall corresponding to this view.
     * 
     * @return
     *          the wall this view is based on
     */
    Wall getWall();

    /**
     * Draw this wall in the canvas.
     */
    void render();

    /**
     * Remove the wall from the canvas.
     */
    void clear();

}
