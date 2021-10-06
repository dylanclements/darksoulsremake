package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Unboxing;

import java.util.Random;

public class OpenChestAction extends Action {
    private final Unboxing unboxing;
    private static final Random r = new Random();

    public OpenChestAction(Unboxing unboxing) {
        this.unboxing = unboxing;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        //Need to know the location?
        float spawnChance = r.nextFloat();
        if (spawnChance <= 0.5f) {
            unboxing.spawnSoulToken();
        } else {
            unboxing.spawnMimic();
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
