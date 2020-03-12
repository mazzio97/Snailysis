package org.snailysis.model.levels.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.snailysis.model.levels.Level;

/**
 * Implementation of the LevelManager which takes care of the unlocked levels
 * and what score you have obtained solving them.
 */
public final class LevelManagerImpl implements LevelManager {

    private static final long serialVersionUID = -4694232779728633012L;

    /**
     * The maximum score you can obtain solving a level.
     */
    public static final int MAX_LEVEL_SCORE = 3;

    private final Map<Level, LevelInfo> manager = new LinkedHashMap<>();

    @Override
    public List<Level> getAllLevels() {
        return manager.keySet().stream().collect(Collectors.toList());
    }

    @Override
    public void insert(final Level lvl, final String lvlName) {
        manager.putIfAbsent(lvl, new LevelInfo(lvlName));
    }

    @Override
    public void remove(final Level lvl) {
        manager.remove(lvl);
    }

    @Override
    public void unlock(final Level lvl) {
        Optional.ofNullable(manager.get(lvl)).ifPresent(i -> i.unlock());
    }

    @Override
    public boolean isUnlocked(final Level lvl) {
        return Optional.ofNullable(manager.get(lvl)).filter(i -> i.isUnlocked()).isPresent();
    }

    @Override
    public int getScore(final Level lvl) {
        return manager.get(lvl).getScore();
    }

    @Override
    public double getTotalScore() {
        return manager.keySet().stream()
                      .mapToInt(this::getScore)
                      .sum() / (getLevelsCount() * MAX_LEVEL_SCORE);
    }

    @Override
    public int getLevelsCount() {
        return manager.size();
    }

    @Override
    public int getUnlockedLevelsCount() {
        return (int) manager.keySet().stream().filter(this::isUnlocked).count();
    }

    @Override
    public void setScore(final Level lvl, final int score) {
        manager.get(lvl).setScore(Optional.of(score));
    }

    @Override
    public Optional<String> getLevelName(final Level lvl) {
        return Optional.ofNullable(manager.get(lvl)).map(l -> l.getName());
    }

    @Override
    public Optional<Level> getLevelFromName(final String lvlName) {
        return manager.entrySet()
                      .stream()
                      .filter(l -> manager.get(l.getKey()).getName().equals(lvlName))
                      .map(l -> l.getKey())
                      .findFirst();
    }

}
