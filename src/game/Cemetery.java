package game;

import edu.monash.fit2099.engine.*;

import java.util.Random;

import static java.lang.Math.random;

public class Cemetery extends Ground {
    private static Random r = new Random();


    /**
     * Constructor.
     *
     * char + to display for this type of terrain
     */
    public Cemetery() {
        super('C');
    }



    //Actor Undead = new Undead("Undead");
    public static void spawnUndead(GameMap gameMap){
        float spawnChance = r.nextFloat();
        if (spawnChance <= 0.25f){
            gameMap.at(9, 7).addActor(new Undead("Undead"));
        }
    }

    @Override
    public void tick(Location location) {
        super.tick(location);
        float spawnChance = r.nextFloat();
        if (spawnChance <= 0.25f){
            location.addActor(new Undead("Undead"));
        }
    }
}
