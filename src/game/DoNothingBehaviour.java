package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Behaviour;

public class DoNothingBehaviour implements Behaviour {

    /**
     * Always generate DoNothingAction
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return DoNothingAction
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return new DoNothingAction();
    }
}
