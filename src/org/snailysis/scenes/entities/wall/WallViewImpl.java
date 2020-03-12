package org.snailysis.scenes.entities.wall;
import java.util.Objects;
import java.util.Optional;

import org.snailysis.model.entities.wall.Wall;

import javafx.scene.canvas.Canvas;

/**
* The corresponding view class of the Wall entity based on WallView interface.
*/
public final class WallViewImpl implements WallView {

   private final Wall w;
   private final Canvas canvas;
   private final Optional<ObstacleImage> top;
   private final Optional<ObstacleImage> bottom;

   /**
    * Constructor of the view of a wall, given the canvas where to render it.
    * 
    * @param w
    *           the wall this view is based on
    * @param canvas
    *           the canvas this view will be rendered inside
    */
   public WallViewImpl(final Wall w, final Canvas canvas) {
       super();
       this.w = w;
       this.canvas = canvas;
       this.top = createImage(canvas.getHeight() - w.getGapY() - w.getGapHeight() / 2);
       this.bottom = createImage(w.getGapY() - w.getGapHeight() / 2);
   }

   @Override
   public void render() {
       top.ifPresent(o -> canvas.getGraphicsContext2D()
                                .drawImage(o, w.getGapX() - Wall.WALL_WIDTH / 2, 0));
       bottom.ifPresent(o -> canvas.getGraphicsContext2D()
                                   .drawImage(o, w.getGapX() - Wall.WALL_WIDTH / 2, canvas.getHeight() - w.getGapY() + w.getGapHeight() / 2));
   }

   @Override
   public void clear() {
       canvas.getGraphicsContext2D()
             .clearRect(w.getGapX() - Wall.WALL_WIDTH / 2, 0, Wall.WALL_WIDTH, canvas.getHeight());
   }

   @Override
   public Wall getWall() {
       return this.w;
   }

   @Override
   public int hashCode() {
       return Objects.hash(w);
   }

   @Override
   public boolean equals(final Object obj) {
       return Optional.ofNullable(obj)
                      .filter(o -> getClass().equals(o.getClass()))
                      .map(o -> (WallViewImpl) o)
                      .filter(o -> o.getWall().equals(getWall())).isPresent();
   }

   private Optional<ObstacleImage> createImage(final double height) {
       return Optional.of(height)
                      .filter(h -> h >= 1)
                      .map(ObstacleImage::new);
   }

}
