package game.interfaces;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import game.LordOfCinder;
import game.Player;

public interface ActiveSkill {
    boolean addCharge();
    boolean charge(Actor actor);
    int getCharges();
    int getMaxCharges();
    boolean windSlash(Actor actor);

    void addActiveSkill(Action action);
}
