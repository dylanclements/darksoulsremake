package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.IBonfire;

public class BonfireTeleportAction extends Action {
    private final IBonfire entryBonfire;
    private final IBonfire exitBonfire;

    public BonfireTeleportAction(IBonfire entryBonfire, IBonfire exitBonfire) {
        this.entryBonfire = entryBonfire;
        this.exitBonfire = exitBonfire;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        map.addActor(actor, exitBonfire.getBonfireLocation());
        return this.menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " teleports from " + entryBonfire.getBonfireName() +
                " to " + exitBonfire.getBonfireName();
    }
}
