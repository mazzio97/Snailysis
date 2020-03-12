package org.snailysis.test.levels;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.snailysis.model.entities.snail.InitialTrajectory;
import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.manager.LevelManager;
import org.snailysis.model.levels.manager.LevelManagerImpl;

/**
 * Test class for the LevelManager.
 */
public class TestLevelManager {

    /**
     * Test all the basic functionalities of the LevelManager.
     */
    @Test
    public void basicLevelManagement() {
        final Level lvl1 = Level.startBuildProcess(InitialTrajectory.CONSTANT).build().build().build();
        final Level lvl2 = Level.startBuildProcess(InitialTrajectory.POSITIVE_DOUBLE_X).build().build().build();
        final Level lvl3 = Level.startBuildProcess(InitialTrajectory.POSITIVE_SQUARED_X).build().build().build();
        final LevelManager lvlMgr = new LevelManagerImpl();

        lvlMgr.insert(lvl1, "1");
        lvlMgr.insert(lvl2, "2");
        lvlMgr.insert(lvl3, "3");
        assertEquals(lvlMgr.getLevelsCount(), 3);
        assertEquals(lvlMgr.getLevelName(lvl2).get(), "2");
        assertEquals(lvlMgr.getLevelFromName("1").get(), lvl1);
        assertFalse(lvlMgr.isUnlocked(lvl1)); // by default levels are locked

        lvlMgr.unlock(lvl1);
        assertTrue(lvlMgr.isUnlocked(lvl1));

        lvlMgr.setScore(lvl1, 2);
        lvlMgr.setScore(lvl2, 3);
        assertEquals(lvlMgr.getScore(lvl1), 2);
        assertEquals(lvlMgr.getScore(lvl2), 0); // a level must be unlocked before setting a score

        lvlMgr.unlock(lvl2);
        lvlMgr.setScore(lvl2, 3);
        assertEquals(lvlMgr.getScore(lvl2), 3);

        lvlMgr.setScore(lvl1, 1);
        assertEquals(lvlMgr.getScore(lvl1), 2); // a score can only improve...

        lvlMgr.setScore(lvl1, 3);
        assertEquals(lvlMgr.getScore(lvl1), 3);

        lvlMgr.remove(lvl3);
        assertEquals(lvlMgr.getLevelsCount(), 2);
    }

}
