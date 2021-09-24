package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Soul;

public class PickUpSoulsAction extends Action {
    private final Location location;
    private final Ground oldGround;

    public PickUpSoulsAction(Location location, Ground oldGround) {
        this.location = location;
        this.oldGround = oldGround;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Only let player do this for now
        SoulToken soulToken = (SoulToken) this.location.getGround();
        soulToken.transferSouls((Soul) actor);
        this.location.setGround(this.oldGround);
        return Message.SOULS_RETRIEVED;
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " picks up soul token";
    }
}
