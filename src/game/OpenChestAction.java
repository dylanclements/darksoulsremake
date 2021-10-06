package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Unboxing;

public class OpenChestAction extends Action {
    private final Unboxing unboxing;

    public OpenChestAction(Unboxing unboxing) {
        this.unboxing = unboxing;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        //Need to know the location?
//        if (this.unboxing.SpawnSoulToken()) {
//
//        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
