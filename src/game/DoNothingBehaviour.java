package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Behaviour;

public class DoNothingBehaviour implements Behaviour {
    @Override
    public Action getAction(Actor actor, GameMap map) {
        return new DoNothingAction();
    }
}
