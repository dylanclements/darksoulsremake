package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Behaviour;


/**
 * Behaviour that generates actions for following + attacking another actor
 * AggroBehaviour is FollowBehaviour + the ability to attack
 */
public class AggroBehaviour implements Behaviour {
    private final FollowBehaviour followBehaviour;
    private final Actor target;

    /**
     * Constructor. AggroBehaviour requires a target and an instance of FollowBehaviour
     * @param subject the actor that will be followed and attacked
     */
    public AggroBehaviour(Actor subject) {
        this.target = subject;
        this.followBehaviour = new FollowBehaviour(this.target);
    }

    /**
     * Action generator.
     * Generate AttackAction if the target is within the aggressor's radius
     * Generate action from FollowBehaviour if not.
     * @param actor the Actor acting
     * @param map the GameMap containing the Actor
     * @return AttackAction or FollowBehaviour action
     */
    @Override
    public Action getAction(Actor actor, GameMap map) {
        if (!map.contains(target) || !map.contains(actor)) {
            return null;
        }

        Location here = map.locationOf(actor);
        Location there = map.locationOf(target);

        // Attack target if actor is adjacent
        for (Exit exit : here.getExits()) {
            Location adjacent = exit.getDestination();
            if (there == adjacent) {
                return new AttackAction(target, exit.getName());
            }
        }
        // Follow the target otherwise
        return followBehaviour.getAction(actor, map);
    }
}
