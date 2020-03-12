package org.snailysis.model.collisions;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.snailysis.model.entities.snail.Snail;
import org.snailysis.model.entities.wall.Wall;

/**
 * Implementation of CheckCollisions interface.
 */
public final class CheckCollisionImpl implements CheckCollisions {
    private static final double PLAY_REGION_WIDTH_MODIFIER = 1.2;
    private final List<Region> regionObstacles = new LinkedList<>();
    private SnailImpact impact;
    private Region regionPlayArea;
    private Region regionSnail;
    private UnaryOperator<Double> ordinateTransf;

    @Override
    public SnailImpact computeCollision(final Snail snail, final Set<Wall> obstacles, final double playAreaWidth, final double playAreaHeight) {
        ordinateTransf = y -> playAreaHeight - y;
        computeRegions(snail, obstacles, playAreaWidth, playAreaHeight);
        computeBorderCollision(snail, playAreaWidth);
        if (!impact.equals(SnailImpact.NOONE)) {
            return impact;
        }
        computeWallCollisions();
        return impact;
    }
    @Override
    public boolean isLevelCompleted() {
       return impact.equals(SnailImpact.END_LEVEL) ? true : false;
    }
    private void computeRegions(final Snail snail, final Set<Wall> obstacles, final double playAreaWidth, final double playAreaHeight) {
        regionPlayArea = new RectangularRegion(0.0, 0.0, playAreaWidth + PLAY_REGION_WIDTH_MODIFIER * snail.getWidth(), playAreaHeight);
        regionSnail = new RectangularRegion(snail.getCurrentX(),
                                            ordinateTransf.apply(snail.getCurrentY()) - snail.getHeight(),
                                            snail.getWidth(), 
                                            snail.getHeight());
        regionSnail.rotate(snail.getAngleDeg());
        obstacles.forEach(o -> {
            if (ordinateTransf.apply(o.getGapY()) > 0.0  && ordinateTransf.apply(o.gapHeightRange().getMaximum()) > 0.0) {
                regionObstacles.add(new RectangularRegion(o.gapWidthRange().getMinimum(),
                                                          0.0,
                                                          Wall.WALL_WIDTH, 
                                                          ordinateTransf.apply(o.gapHeightRange().getMaximum())));
            }
            if (ordinateTransf.apply(o.getGapY()) < playAreaHeight && ordinateTransf.apply(o.gapHeightRange().getMinimum()) < playAreaHeight) {
                regionObstacles.add(new RectangularRegion(o.gapWidthRange().getMinimum(),
                                                         ordinateTransf.apply(o.gapHeightRange().getMinimum()),
                                                         Wall.WALL_WIDTH, 
                                                         playAreaHeight - ordinateTransf.apply(o.gapHeightRange().getMinimum())));
            }
        });
    }
    private void computeBorderCollision(final Snail snail, final double playAreaWidth) {
        impact = regionPlayArea.contains(regionSnail) ? SnailImpact.NOONE : SnailImpact.PLAYAREA;
        if (impact.equals(SnailImpact.PLAYAREA) && snail.getCurrentX() >= playAreaWidth) {
            impact = SnailImpact.END_LEVEL;
        }
        if (impact.equals(SnailImpact.PLAYAREA) && snail.getCurrentX() <= snail.getWidth()) {
            impact = SnailImpact.NOONE;
        }
    }
    private void computeWallCollisions() {
        impact = regionObstacles.stream().filter(o -> regionSnail.collide(o)).findFirst().isPresent() ? SnailImpact.WALL : SnailImpact.NOONE;
    }
}
