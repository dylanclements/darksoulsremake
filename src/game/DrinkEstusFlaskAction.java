package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Consumable;

public class DrinkEstusFlaskAction extends Action {
    private final Consumable estusFlask;

    public DrinkEstusFlaskAction(Consumable estusFlask) {
        this.estusFlask = estusFlask;
    }

    @Override
    public String hotkey() {
        return "E";
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (estusFlask.consumedBy(actor)) {
            return actor + " drank Estus Flask";
        } else {
            return actor + " Estus Flask is empty";
        }
    }

    @Override
    public String menuDescription(Actor actor) {
        Consumable estus = this.estusFlask;
        return String.format("%s drinks Estus Flask (%d/%d)", actor, estus.getCharges(), estus.getMaxCharges());
    }
}
