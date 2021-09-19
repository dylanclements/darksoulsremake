package game.interfaces;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.LordOfCinder;

public interface ActiveSkill {
    boolean charge(Actor actor);
    int getCharges();
    int getMaxCharges();
    String windSlash(Actor actor, GameMap map, LordOfCinder yhorm, String direction);

    void addActiveSkill(Action action);
}
