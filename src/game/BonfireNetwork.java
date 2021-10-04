package game;

import edu.monash.fit2099.engine.Actions;
import game.interfaces.IBonfire;

import java.util.ArrayList;
import java.util.List;

public class BonfireNetwork {
    private static BonfireNetwork instance;
    private List<IBonfire> bonfireList;

    public static BonfireNetwork getInstance() {
        if (instance == null) {
            instance = new BonfireNetwork();
        }
        return instance;
    }

    /**
     * Constructor.
     */
    private BonfireNetwork() {
        this.bonfireList = new ArrayList<>();
    }

    /**
     * Add a bonfire to the bonfire list.
     * @param bonfire a bonfire
     */
    public void addBonfire(IBonfire bonfire) {
        this.bonfireList.add(bonfire);
    }

    /**
     * Get all possible teleport actions to different bonfires
     * @param entryBonfire bonfire to be teleported from
     * @return list of all BonfireTeleportActions available.
     */
    public Actions getTeleportActions(IBonfire entryBonfire) {
        Actions actions = new Actions();
        for (IBonfire exitBonfire : this.bonfireList) {
            if (exitBonfire.getBonfireLocation() != entryBonfire.getBonfireLocation()) {
                actions.add(new BonfireTeleportAction(entryBonfire, exitBonfire));
            }
        }
        return actions;
    }
}
