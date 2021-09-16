package game;

import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Resettable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A global Singleton manager that does soft-reset on the instances.
 * TODO: you may modify (add or remove) methods in this class if you think they are not necessary.
 * HINT: refer to Bootcamp Week 5 about static factory method.
 * A3: Think about how will you improve this implementation in the future assessment.
 * What could be the drawbacks of this implementation?
 */
public class ResetManager {
    /**
     * A list of resettable instances (any classes that implements Resettable,
     * such as Player implements Resettable will be stored in here)
     */
    private List<Resettable> resettableList;

    /**
     * A singleton reset manager instance
     */
    private static ResetManager instance;

    /**
     * Get the singleton instance of reset manager
     * @return ResetManager singleton instance
     */
    public static ResetManager getInstance(){
        if(instance == null){
            instance = new ResetManager();
        }
        return instance;
    }

    /**
     * Constructor
     */
    private ResetManager(){
        resettableList = new ArrayList<>();
    }

    /**
     * Reset the game by traversing through all the list
     * By doing this way, it will avoid using `instanceof` all over the place.
     * FIXME: it does nothing, you need to implement it :)
     */
    public void run(GameMap map){
        for (Resettable resettable : resettableList) {
            resettable.resetInstance(map);
        }
        this.cleanUp();
    }

    /**
     * FOR DEBUGGING ONLY: print all the resettable instances to the console
     */
    protected void printResettable() {
        System.out.println(Arrays.toString(resettableList.toArray()));
    }

    /**
     * Add the Resettable instance to the list
     * @param resettable the interface instance
     */
    public void appendResetInstance(Resettable resettable){
        resettableList.add(resettable);
    }

    /**
     * clean up instances (actor, item, or ground) that doesn't exist anymore in the map
     * i.e. Remove items from resettableList that do not exist anymore
     */
    private void cleanUp(){
        ArrayList<Resettable> keep = new ArrayList<>();
        for (Resettable resettable : resettableList) {
            if (resettable.isExist()) {
                keep.add(resettable);
            }
        }
        resettableList = new ArrayList<>();
        resettableList.addAll(keep);
    }
}
