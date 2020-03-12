package org.snailysis.scenes.levels.manager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.snailysis.model.levels.Level;
import org.snailysis.model.levels.manager.LevelManager;
import org.snailysis.model.levels.manager.LevelManagerImpl;
import org.snailysis.model.utilities.Pair;
import org.snailysis.scenes.utilities.save.SaveCustomBuild;
import org.snailysis.scenes.utilities.save.SaveCustomBuildimpl;

import org.snailysis.scenes.Controller;
import org.snailysis.scenes.gameplay.PlayLevelController;
import org.snailysis.scenes.gameplay.PlayLevelControllerImpl;

/**
 * Implementation of LevelBuilderController, used to manage available levels in the game.
 */
public final class LevelManagerControllerImpl implements LevelManagerController {

    private static final int NUM_DEFAULT_LEVELS = 15;
    private static final String DEFAULT_LEVELS = "/org/snailysis/file/";

    private final LevelManager customLvlMgr;
    private final SaveCustomBuild<Level> lvlLoader = new SaveCustomBuildimpl<>();
    private final SaveCustomBuild<LevelManager> managerLoader = new SaveCustomBuildimpl<>();
    private final Controller mainCtrl;

    /**
     * Constructor of the LevelManagerController, 
     * appointed to load the levels and the progresses of the user.
     * 
     * @param ctrl
     *          the main controller of the game
     */
    public LevelManagerControllerImpl(final Controller ctrl) {
        this.mainCtrl = ctrl;
        this.customLvlMgr = ctrl.getModel().getCustomManager();
        /*
         * Load user progresses or create the file if not present.
         */
        Optional.of(new File(SAVE_PATH)).filter(f -> !f.exists()).filter(f -> f.mkdir());
        Optional.of(new File(DEFAULT_LEVELS_PATH)).filter(f -> !f.exists()).filter(f -> f.mkdir()).ifPresent(f -> {
            IntStream.rangeClosed(1, NUM_DEFAULT_LEVELS)
                     .mapToObj(i -> String.format("%02d" + LEVEL_EXTENSION, i))
                     .forEach(n -> {
                         try {
                            Files.copy(getClass().getResourceAsStream(DEFAULT_LEVELS + n), Paths.get(DEFAULT_LEVELS_PATH + n),
                                       StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                     });
        });
        if (!new File(DEFAULT_SAVE_FILE).exists()) {
            final LevelManager tmpMgr = new LevelManagerImpl();
            loadAllLevelsFromPath(DEFAULT_LEVELS_PATH, (f1, f2) -> f1.toString().compareTo(f2.toString()))
                                 .forEach(p -> tmpMgr.insert(p.getFirst(), p.getSecond()));
            tmpMgr.unlock(tmpMgr.getAllLevels().get(0));
            managerLoader.save(tmpMgr, DEFAULT_SAVE_FILE);
        }
        /*
         * Load the default levels only the first time this controller is called.
         */
        if (mainCtrl.getModel().getDefaultManager().getAllLevels().isEmpty()) {
            try {
                mainCtrl.getModel().setDefaultManager(managerLoader.load(DEFAULT_SAVE_FILE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
         * Load user custom levels in cronological order.
         */
        Optional.of(new File(CUSTOM_LEVELS_PATH)).filter(f -> !f.exists()).filter(f -> f.mkdir());
        loadAllLevelsFromPath(CUSTOM_LEVELS_PATH, (f1, f2) -> compareByCreationDate(f1, f2))
                             .stream()
                             .peek(p -> customLvlMgr.insert(p.getFirst(), p.getSecond()))
                             .map(Pair::getFirst)
                             .forEach(l -> customLvlMgr.unlock(l));
    }

    @Override
    public List<Level> getDefaultLevels() {
        return mainCtrl.getModel().getDefaultManager().getAllLevels();
    }

    @Override
    public List<Level> getCustomLevels() {
        return customLvlMgr.getAllLevels();
    }

    @Override
    public PlayLevelController selectLevelToPlay(final Level lvl) {
        mainCtrl.getModel().selectLevel(lvl);
        return new PlayLevelControllerImpl(mainCtrl);
    }

    @Override
    public String getLevelName(final Level lvl) { 
        return mainCtrl.getModel().getDefaultManager().getLevelName(lvl)
                       .orElse(customLvlMgr.getLevelName(lvl).orElse(null));
    }

    @Override
    public boolean isUnlocked(final Level lvl) {
        return mainCtrl.getModel().getDefaultManager().isUnlocked(lvl) || customLvlMgr.isUnlocked(lvl);
    }

    private List<Pair<Level, String>> loadAllLevelsFromPath(final String path, final Comparator<Path> cmp) {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            return paths.filter(Files::isRegularFile)
                        .sorted(cmp)
                        .map(f -> Optional.ofNullable(loadLevel(f))
                                          .map(l -> new Pair<>(l, Optional.ofNullable(f.getFileName())
                                                                          .map(n -> n.toString())
                                                                          .map(n -> n.substring(0, n.lastIndexOf(".")))
                                                                          .orElse(null))))
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private Level loadLevel(final Path f) {
        try {
            return lvlLoader.load(f.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private int compareByCreationDate(final Path f1, final Path f2) {
        try {
            final BasicFileAttributes attr1 = Files.readAttributes(f1, BasicFileAttributes.class);
            final BasicFileAttributes attr2 = Files.readAttributes(f2, BasicFileAttributes.class);
            return attr1.lastModifiedTime().compareTo(attr2.lastModifiedTime());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
