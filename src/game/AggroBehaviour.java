package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Behaviour;


public class AggroBehaviour implements Behaviour {
    private final FollowBehaviour followBehaviour;
    private final Actor target;

    public AggroBehaviour(Actor subject) {
        this.target = subject;
        this.followBehaviour = new FollowBehaviour(this.target);
    }

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
