package org.snailysis.scenes.entities.wall;

import java.util.Random;

import org.snailysis.model.entities.wall.Wall;
import org.snailysis.scenes.entities.EntitySprite;

import javafx.scene.image.WritableImage;

/**
 * A WritableImage created from a sprite and cut to represent a generic game obstacle.
 */
public class ObstacleImage extends WritableImage {

    /**
     * Constructor of the image of a generic obstacle in the game.
     * 
     * @param height
     *          the height of the image to create
     */
    public ObstacleImage(final double height) {
        super(EntitySprite.WALL.getImage().getPixelReader(), 
              new Random().nextInt((int) (EntitySprite.WALL.getImage().getWidth() - Wall.WALL_WIDTH)),
              0, 
              (int) Wall.WALL_WIDTH,
              (int) height);
    }

}
