package game;

import java.util.ArrayList;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.interfaces.Behaviour;

/**
 * Behaviour for actors to wander around the map aimlessly.
 */
public class WanderBehaviour extends Action implements Behaviour {
	
	private final Random random = new Random();

	/**
	 * Returns a MoveAction to wander to a random location, if possible.  
	 * If no movement is possible, returns null.
	 * 
	 * @param actor the Actor enacting the behaviour
	 * @param map the map that actor is currently on
	 * @return an Action, or null if no MoveAction is possible
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		ArrayList<Action> actions = new ArrayList<Action>();

		// FIXME: figure out how to stop actors from wandering into firelink shrine
		for (Exit exit : map.locationOf(actor).getExits()) {
            Location destination = exit.getDestination();
            if (destination.canActorEnter(actor)) {
            	actions.add(exit.getDestination().getMoveAction(actor, "around", exit.getHotKey()));
            }
        }
		
		if (!actions.isEmpty()) {
			return actions.get(random.nextInt(actions.size()));
		}
		else {
			return null;
		}

	}

	/**
	 * Description of wandering
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return string that describes
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		return menuDescription(actor);
	}

	/**
	 * menu prompt to wander
	 * @param actor The actor performing the action.
	 * @return string that describes the menu prompt
	 */
	@Override
	public String menuDescription(Actor actor) {
		return "Raagrh...";
	}
}
