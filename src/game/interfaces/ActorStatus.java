package game.interfaces;

import edu.monash.fit2099.engine.WeaponItem;

public interface ActorStatus {
    int getHitPoints();
    int getMaxHitPoints();
    WeaponItem getWeapon();
}
