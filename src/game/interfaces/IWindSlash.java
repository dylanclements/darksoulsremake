package game.interfaces;

import edu.monash.fit2099.engine.Actor;

public interface IWindSlash {
    boolean charge(Actor actor);

    int getCharges();

    int getMaxCharges();

    void prepareWindSlash(Actor actor);

    void finishWindSlash(Actor actor);
}