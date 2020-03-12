package org.snailysis.scenes.entities;

import org.snailysis.model.Dimension;

import javafx.scene.image.Image;

/**
 * A list of the sprites used in the game in order to load them only once.
 */
public enum EntitySprite {

    /**
     * The default obstacle of the game.
     */
    WALL("wall.jpg"),
    /**
     * The main character of the game.
     */
    SNAIL("snail.png", Dimension.SNAIL_HEIGHT.get() + 2, Dimension.SNAIL_WIDTH.get() + 8),
    /**
     * The axis' origin sprite.
     */
    ORIGIN("origin.png", Dimension.SNAIL_WIDTH.get(), Dimension.SNAIL_WIDTH.get()),
    /**
     * Game over gif.
     */
    GAME_OVER("gameOver.gif"),
    /**
     * Level complete gif.
     */
    LEVEL_COMPLETE("levelComplete.gif");
    /**
     * The default path where the images are loaded from.
     */
    public static final String IMG_PATH = "org/snailysis/images/";
    private final Image img;

    /**
     * 
     * @param imgFileName
     *          the name of the file corresponding to the image to load
     */
    EntitySprite(final String imgFileName) {
        this.img = new Image(IMG_PATH + imgFileName);
    }

    /**
     * 
     * @param imgFileName
     *          the name of the file corresponding to the image to load
     * @param height
     *          the height of the sprite
     * @param width
     *          the width of the sprite
     */
    EntitySprite(final String imgFileName, final double height, final double width) {
        this.img = new Image(IMG_PATH + imgFileName, width, height, true, true);
    }

    /**
     * 
     * @return
     *          the image contained in the enum
     */
    public Image getImage() {
        return this.img;
    }
}
