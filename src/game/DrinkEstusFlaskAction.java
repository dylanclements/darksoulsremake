package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Consumable;

public class DrinkEstusFlaskAction extends Action {
    private Actor target;
    private Consumable estusFlask;

    public DrinkEstusFlaskAction(Consumable estusFlask) {
        this.estusFlask = estusFlask;
    }

    @Override
    public String hotkey() {
        return "E";
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        estusFlask.reduceCharges();
        //how to display charges
        return actor + " drank Estus Flask";
    }

    @Override
    public String menuDescription(Actor actor) {
        return actor + " Drinks Estus Flask";
    }
}
