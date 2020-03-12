package org.snailysis.scenes.gameplay;

import org.snailysis.model.Model;
import org.snailysis.model.collisions.SnailImpact;
import org.snailysis.scenes.Controller;
import org.snailysis.scenes.View;
import org.snailysis.scenes.entities.snail.SnailControllerImpl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javafx.application.Platform;

/**
 * Implementation of GameLoop.
 */
public final class GameLoopImpl extends Thread implements GameLoop {
    private static final long SLEEP_TIME = (long) 40.0;
    private final Map<SnailImpact, List<Runnable>> impactObservers = new HashMap<>();
    private final Map<Integer, List<Runnable>> controlsObservers = new HashMap<>();
    private final Controller controller;
    private final Model model;
    private final View view;
    private boolean running;
    private int iterations;

    /**
     * GameLoopImpl constructor.
     * @param controller
     *          main controller
     */
    public GameLoopImpl(final Controller controller) {
        super();
        this.running = false;
        this.controller = controller;
        this.model = controller.getModel();
        this.view = controller.getView();
        this.iterations = 0;
        this.setDaemon(true);
    }
    /**
     * Main method of the loop. Update and render the game.
     */
    public void run() {
        this.running = true;
        new SnailControllerImpl(controller).startDrawing();
        while (running) {
            Platform.runLater(() -> {
                iterations++;
                notifyCollisionsObservers(iterations);
                if (!model.checkCollisions().equals(SnailImpact.NOONE)) {
                    notifyObservers(model.checkCollisions());
                    finish();
                } else {
                    notifyObservers(SnailImpact.NOONE);
                }
                Optional.ofNullable(view.getCanvas()).ifPresent(c -> c.requestFocus());
            });
            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            this.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean isRunning() {
        return this.running;
    }
    @Override
    public synchronized void finish() {
        this.impactObservers.clear();
        controlsObservers.values().stream()
                                  .flatMap(l -> l.stream())
                                  .forEach(Runnable::run);
        this.running = false;
    }
    @Override
    public void registerCollisionsObserver(final SnailImpact impact, final Runnable observer) {
        final List<Runnable> oneObserver = new LinkedList<>(Arrays.asList(observer));
        this.impactObservers.merge(impact, oneObserver, (v, v1) -> {
            v.addAll(v1); 
            return v; 
        });
    }
    @Override
    public void registerControlsObserver(final long time, final Runnable observer) {
        final List<Runnable> oneObserver = new LinkedList<>(Arrays.asList(observer));
        this.controlsObservers.merge((int) (time / SLEEP_TIME + iterations), oneObserver, (v, v1) -> {
            v.addAll(v1); 
            return v; 
        });
    }
    @Override
    public boolean isControlsObserversEmpty() {
        return controlsObservers.isEmpty();
    }
    @Override
    public boolean isCollisionsObserversEmpty() {
        return impactObservers.isEmpty();
    }

    private void notifyObservers(final SnailImpact impact) {
        this.impactObservers.getOrDefault(impact, new LinkedList<>()).forEach(Runnable::run);
    }

    private void notifyCollisionsObservers(final int iteration) {
        this.controlsObservers.getOrDefault(iteration, new LinkedList<>()).forEach(Runnable::run);
    }
}
