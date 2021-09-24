package game;

import java.util.Random;

public abstract class Sword extends MeleeWeapon {
    private static final Random r = new Random();
    /**
     * Constructor.
     *
     *
     * @param name
     * @param displayChar
     * @param damage
     * @param verb
     * @param hitRate
     */
    public Sword(String name, char displayChar, int damage, String verb, int hitRate) {
        super(name,displayChar,damage,verb,hitRate);

    }

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
