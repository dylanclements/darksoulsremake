package game.exceptions;

/**
 * Exception for when a weapon should exist but doesn't
 */
public class MissingWeaponException extends Exception {
    public MissingWeaponException(String message) {
        super(message);
    }
}
