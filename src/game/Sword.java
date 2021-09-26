package game;

import java.util.Random;

/**
 * Swords have critical strike
 */
public abstract class Sword extends MeleeWeapon {
    private static final Random r = new Random();

    /**
     * Constructor.
     * @param name name of this sword
     * @param displayChar representation if dropped on the ground
     * @param damage damage this sword will deal
     * @param verb description of this swords attack
     * @param hitRate % chance this sword will deal damage
     */
    public Sword(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name, displayChar, damage, verb, hitRate);

    }

    /**
     * Give all swords a 20% chance to deal double damage with a normal attack.
     * @return either normal damage or double damage
     */
    @Override
    public int damage() {
        float chanceToCriticalHit = r.nextFloat();
        if (chanceToCriticalHit <= 0.2){
            return super.damage()*2;
        }
        else {
            return super.damage();
        }

    }
}
