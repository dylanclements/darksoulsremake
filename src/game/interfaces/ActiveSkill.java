package game.interfaces;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import game.LordOfCinder;

public interface ActiveSkill {
    boolean charge(Actor actor);
    int getCharges();
    int getMaxCharges();
    boolean windSlash(LordOfCinder yhorm);

    void addActiveSkill(Action action);
}
