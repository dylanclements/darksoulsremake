package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Behaviour;

/**
 * A class that figures out a MoveAction that will move the actor one step 
 * closer to a target Actor.
 */
public class FollowBehaviour implements Behaviour {

	private final Actor target;

	/**
	 * Constructor.
	 * 
	 * @param subject the Actor to follow
	 */
	public FollowBehaviour(Actor subject) {
		this.target = subject;
	}

	/**
	 * Generate actions that follow the target
	 * @param actor the Actor acting
	 * @param map the GameMap containing the Actor
	 * @return action that corresponds to following target
	 */
	@Override
	public Action getAction(Actor actor, GameMap map) {
		if(!map.contains(target) || !map.contains(actor))
			return null;
		
		Location here = map.locationOf(actor);
		Location there = map.locationOf(target);

		int currentDistance = Utils.distance(here, there);
		for (Exit exit : here.getExits()) {
			Location destination = exit.getDestination();
			if (destination.canActorEnter(actor)) {
				int newDistance = Utils.distance(destination, there);
				if (newDistance < currentDistance) {
					return new MoveActorAction(destination, exit.getName());
				}
			}
		}
		// do nothing if actor is blocked
		return new DoNothingAction();
	}
}