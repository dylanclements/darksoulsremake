package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.interfaces.Soul;

public class SoulToken extends Ground implements Soul {
    private int souls;
    private final Ground oldGround;

    public SoulToken(Ground oldGround) {
        super('$');
        this.souls = 0;
        this.oldGround = oldGround;
    }

    public SoulToken(Ground oldGround, int souls) {
        super('$');
        this.souls = souls;
        this.oldGround = oldGround;
    }

    public int getSouls() {
        return this.souls;
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new PickUpSoulsAction(location, this.oldGround, this.souls));
        return actions;
    }

    @Override
    public void transferSouls(Soul soulObject) {
        int tokenSouls = getSouls();
        this.subtractSouls(tokenSouls);
        soulObject.addSouls(tokenSouls);
    }

    @Override
    public boolean subtractSouls(int souls) {
        if (souls > this.getSouls()) {
            return false;
        }
        this.souls -= souls;
        return true;
    }

    @Override
    public boolean addSouls(int souls) {
        this.souls += souls;
        return true;
    }
}
