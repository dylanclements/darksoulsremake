package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

/**
 * Ground type that spawns undead.
 */
public class Cemetery extends Ground {
    private static final Random r = new Random();


    /**
     * Constructor.
     *
     * char + to display for this type of terrain
     */
    public Cemetery() {
        super('C');
    }

    /**
     * Every game tick, 25% chance to spawn an undead
     * @param location The location of the Ground
     */
    @Override
    public void tick(Location location) {
        super.tick(location);
        float spawnChance = r.nextFloat();
        if (spawnChance <= 0.25f && !location.containsAnActor()) {
            location.addActor(new Undead("Undead"));
        }
    }
}
