package org.snailysis.model.levels.manager;

import java.io.Serializable;
import java.util.Optional;

class LevelInfo implements Serializable {

    private static final long serialVersionUID = -3993304801855421107L;

    private final String name;
    private boolean unlocked;
    private int score;

    LevelInfo(final String lvlName) {
        this.name = lvlName;
    }

    public void setScore(final Optional<Integer> score) {
        if (score.filter(s -> unlocked)
                 .filter(s -> s > 0)
                 .filter(s -> s > getScore())
                 .filter(s -> s <= LevelManagerImpl.MAX_LEVEL_SCORE)
                 .isPresent()) {
            this.score = score.get();
        }
    }

    public void unlock() {
        this.unlocked = true;
    }

    public int getScore() {
        return this.score;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public boolean isCompleted() {
        return this.score > 0;
    }

    public String getName() {
        return this.name;
    }
}
