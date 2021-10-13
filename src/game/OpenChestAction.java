package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Unboxing;

import java.util.Random;

public class OpenChestAction extends Action {
    private static final float CHEST_CHANCE = 0.5f;

    private final Unboxing unboxing;
    private static final Random r = new Random();

    public OpenChestAction(Unboxing unboxing) {
        this.unboxing = unboxing;
    }

    /**
     * Execute opening a chest. 50% chance to be soul tokens, 50% chance to be a mimic.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return a string describing this action.
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        float spawnChance = r.nextFloat();
        if (spawnChance <= OpenChestAction.CHEST_CHANCE) {
            unboxing.spawnSoulToken();
            return "The chest contained Soul Tokens!";
        } else {
            unboxing.spawnMimic();
            return "The chest was a Mimic!";
        }
    }

    /**
     * Prompt to execute this actions
     * @param actor The actor performing the action.
     * @return a string that describe what this action will do.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " opens the chest";
    }
}
