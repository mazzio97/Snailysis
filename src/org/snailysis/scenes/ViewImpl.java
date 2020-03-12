package org.snailysis.scenes;


import org.snailysis.scenes.entities.snail.SnailDrawer;
import org.snailysis.scenes.entities.snail.SnailDrawerImpl;
import javafx.scene.canvas.Canvas;

/**
 * Basic implementation of the View interface.
 */
public final class ViewImpl implements View {

    private Canvas canvas;

    @Override
    public SnailDrawer getSnailDrawer() {
        return new SnailDrawerImpl(getCanvas());
    }

    @Override
    public void setCanvas(final Canvas c) {
        this.canvas = c;
    }

    @Override
    public Canvas getCanvas() {
        return canvas;
    }

}
