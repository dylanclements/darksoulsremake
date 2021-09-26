package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.Consumable;

public class DrinkEstusFlaskAction extends Action {
    private final Consumable estusFlask;

    /**
     * Constructor.
     * @param estusFlask consumable item
     */
    public DrinkEstusFlaskAction(Consumable estusFlask) {
        this.estusFlask = estusFlask;
    }

    /**
     * Allocate hotkey for this action
     * @return string
     */
    @Override
    public String hotkey() {
        return "E";
    }

    /**
     * Consume the estus flask
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string that describes this action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (estusFlask.consumedBy(actor)) {
            return actor + " drank Estus Flask";
        } else {
            return actor + " Estus Flask is empty";
        }
    }

    /**
     * Menu prompt for this action
     * @param actor The actor performing the action.
     * @return string that describes what this action will do
     */
    @Override
    public String menuDescription(Actor actor) {
        Consumable estus = this.estusFlask;
        return String.format("%s drinks Estus Flask (%d/%d)", actor, estus.getCharges(), estus.getMaxCharges());
    }
}
