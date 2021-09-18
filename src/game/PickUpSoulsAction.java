package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Soul;

public class PickUpSoulsAction extends Action {
    private final Location location;
    private final Ground oldGround;
    private final int souls;

    public PickUpSoulsAction(Location location, Ground oldGround, int souls) {
        this.location = location;
        this.oldGround = oldGround;
        this.souls = souls;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        // Only let player do this for now
        SoulToken soulToken = (SoulToken) this.location.getGround();
        soulToken.transferSouls((Soul) actor);
        this.location.setGround(this.oldGround);
        return menuDescription(actor);
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " picks up soul token";
    }

    public int getSouls() {
        return souls;
    }
}
