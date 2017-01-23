//PokemonMove class
package pokemon.data;

import serialization.srsf.Lazy;

/**
 * Represents a move a Pokemon can use in battle
 */
public class PokemonMove {
    /**
     * The name of the move
     */
    private String name;
    /**
     * The to-be-resolved type of the move
     */
    private Lazy<PokemonType> type;
    /**
     * The base damage this move does
     */
    private double baseDamage;
    /**
     * The self damage this move does.
     */
    private double selfDamage;

    /**
     * Instamtiates a move
     * @param name The name of the move
     * @param type The lazily-resolved type of the move
     * @param damage The base damage this move does
     * @param selfDamage The damage the move inflicts on its user
     */
    public PokemonMove(String name, Lazy<PokemonType> type, double damage, double selfDamage) {
        this.name = name;
        this.type = type;
        baseDamage = damage;
        this.selfDamage = selfDamage;
    }

    /**
     * Gets the name of the move
     * @return The name of the moves
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the amount of damage the move does to it's target
     * @return The amount of damage the move does to it's target
     */
    public int getDamage() {
        return (int) Math.round(baseDamage);
    }

    /**
     * Gets the amount of damage the move does to it's user
     * @return The amount of damage the move does to it's user.
     */
    public int getSelfDamage() {

        return (int) Math.round(selfDamage);
    }

    /**
     * Gets the type of the move
     * @return The type of the move
     */
    public PokemonType getType() {
        return type.getValue();
    }

    /**
     * Checks equality for a move
     * @param move The moves to compare
     * @return Whether or not the moves are the same move
     */
    public boolean equals(PokemonMove move) {
        return (move!= null && move.getName().equals(getName()));
    }

    /**
     * Checks equality for an object if the object of type PokemonMove
     * @param o an object
     * @return True if the object is a PokemonMove and is equal to this move, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof PokemonMove) return this.equals((PokemonMove)o);
        return false;
    }

    /**
     * Returns a string representation of the move, with it's type
     * @return A string representation of the move, with it's type.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + this.getType() + "] ");
        sb.append(this.name);
        return sb.toString();
    }
}