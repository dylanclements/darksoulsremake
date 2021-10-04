package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.IBonfire;

/**
 * Resting place for the player
 */
public class Bonfire extends Ground implements IBonfire {
    private boolean lit;
    private final Location location;
    private final String name;

    /**
     * Constructor.
     * Bonfire is not lit by default.
     */
    public Bonfire(Location location, String name) {
        super('B');
        this.location = location;
        this.name = name;
        this.lit = false;
    }

    /**
     * Actor can enter bonfire (changed in Assignment 3)
     * @param actor the Actor to check
     * @return true
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return true;
    }

    /**
     * Should block thrown objects
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }

    /**
     * Offer to light bonfire if not lit to player, else offer rest if player, else nothing.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return Actions list containing available bonfire actions.
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();

        if (actor instanceof Player) {
            if (this.lit) {
                // Give player the rest action if bonfire is lit
                actions.add(new BonfireRestAction());

                // Get all teleport actions from the BonfireNetwork if lit.
                BonfireNetwork bonfireNetwork = BonfireNetwork.getInstance();
                Actions teleportActions = bonfireNetwork.getTeleportActions(this);
                for (Action teleportAction : teleportActions) {
                    actions.add(teleportAction);
                }

            } else {
                actions.add(new BonfireLightAction(this));
            }
        }

        return actions;
    }

    /**
     * @return true if the bonfire is lit else false
     */
    public boolean isLit() {
        return lit;
    }

    /**
     * @return the location the bonfire is sitting upon
     */
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean lightBonfire() {
        boolean oldLit = this.lit;
        this.lit = true;
        return !oldLit;
    }

    @Override
    public String getBonfireName() {
        return this.name;
    }

    @Override
    public Location getBonfireLocation() {
        return this.location;
    }
}
