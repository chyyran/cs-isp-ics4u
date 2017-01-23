// Pokemon Class

package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Represents a Pokemon in a player's team
 */
public class Pokemon {
    /**
     * The tag to display for fainted Pokemon
     */
    private static final String STR_FAINT_TAG = " [FNT]";
    /**
     * The tag to display for the Pokemon's level
     */
    private static final String STR_LEVEL_TAG = "Lv.";
    /**
     * The to-be-resolved value of the Pokemon's species
     */
    private Lazy<PokemonSpecies> species;
    /**
     * The to-be-resolved list of moves this pokemon has
     */
    private List<Lazy<PokemonMove>> moves;
    /**
     * The name of the Pokemon
     */
    private String name;
    /**
     * The level of the Pokemon
     */
    private int level;
    /**
     * The unique id of the Pokemon
     */
    private String id;
    /**
     * The maximum HP of the Pokemon. This is calculated mathematically and is unique for every Pokemon.
     */
    private int maxHp;
    /**
     * The current HP of the Pokemon
     */
    private int currentHp;

    /**
     * The maximum HP multiplier, this is the maximum HP a Pokemon at level one could possibly have, with the addition
     * of MAX_BUFF
     */
    private static int MULTIPLIER = 100;
    /**
     * The maximum HP buff, this is the maximum amount of HP a Pokemon is allowed to deviate positively
     * from at level one, thus a Pokemon at level one could only possibly have 150 HP at most.
     */
    private static int MAX_BUFF = 50;
    /**
     * The random percentage of HP
     */
    private static int RANDOM_BOUND = 65;

    /**
     * Instantiates a Pokemon
     * @param id The unique ID of the Pokemon
     * @param species The species of the Pokemon
     * @param moves The moves this Pokemon can use
     * @param name The nickname of the Pokemon
     * @param level The level of the Pokemon
     */
    public Pokemon(String id, Lazy<PokemonSpecies> species, List<Lazy<PokemonMove>> moves, String name, int level) {
        this(id, species, moves, name, level, getMaxHp(id, level));
    }

    /**
     * Instantiates a Pokemon with a given current HP value
     * @param id The unique ID of the Pokemon
     * @param species The species of the Pokemon
     * @param moves The moves this Pokemon can use
     * @param name The nickname of the Pokemon
     * @param level The level of the Pokemon
     * @param hp The current HP of the Pokemon
     */
    public Pokemon(String id, Lazy<PokemonSpecies> species, List<Lazy<PokemonMove>> moves, String name, int level, int hp) {
        this.id = id;
        this.species = species;
        this.moves = moves;
        this.name = name;
        this.level = level;
        this.id = id;
        maxHp = getMaxHp(id, level);
        currentHp = hp;
    }

    /**
     * Gets the maximum HP for a Pokemon with a given ID
     * @param id The String ID of the Pokemon
     * @param level The level of the Pokemon
     * @return The maximum HP for this Pokemon.
     */
    private static int getMaxHp(String id, int level) {
        Random r = new Random(id.hashCode()); //Using a repeatable seed allows for deterministic pseudo-random numbers
        int buffFactor = r.nextInt(MULTIPLIER);
        int buff = (MAX_BUFF * buffFactor) / (MAX_BUFF + buffFactor);
        int maxHp = Math.abs(level * (int)(MULTIPLIER * (r.nextInt(RANDOM_BOUND) * 0.01)) +
                (int)(buff * ((r.nextInt(RANDOM_BOUND) * 0.01) + (MAX_BUFF / (double)MULTIPLIER))));
        return maxHp;
    }

    /**
     * Gets the species of the Pokemon
     * @return The species of the Pokemon
     */
    public PokemonSpecies getSpecies() {
        return species.getValue();
    }

    /**
     * Gets the moves of the Pokemon
     * @return The moves of the Pokemon
     */
    public List<PokemonMove> getMoves() {
        return Lazy.asList(this.moves);
    }

    /**
     * Gets the nickname of the Pokemon
     * @return The nickname of the Pokemon
     */
    public String getNickname() {
        return name;
    }

    /**
     * Sets the nickname of the Pokemon
     * @param nickName The nickname of the Pokemon
     */
    public void setNickname(String nickName) {
        this.name = nickName + " ("+this.getSpecies().getName()+")";
    }

    /**
     * Gets the level of the Pokemon
     * @return The level of the Pokemon
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the Pokemon and updates the maxHP accordingly
     * @param num The level of the Pokemon
     */
    public void setLevel(int num) {
        level = num;
        maxHp = getMaxHp(this.getId(), level);
    }

    /**
     * Gets the maximum HP of the Pokemon
     * @return The maxmimum HP of the Pokemon
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Gets the current HP of the Pokemon
     * @return The current HP of the Pokemon
     */
    public int getCurrentHp() {
        return currentHp;
    }

    /**
     * Sets the HP of the Pokemon
     * @param num The new HP of the Pokemon
     */
    public void setHp(int num) {
        if (num < maxHp) {
            currentHp = num;
        } else {
            currentHp = maxHp;
        }
        if(currentHp < 0) {
            currentHp = 0;
        }
    }

    /**
     * Returns a string representation of the Pokemon and it's stats
     * @return A string representation of the Pokemon and it's stats.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(STR_LEVEL_TAG);
        sb.append(this.getLevel());
        sb.append(" " + this.getNickname());
        sb.append(" " + this.getCurrentHp() + "/" + this.getMaxHp());
        if(this.isFainted()) {
            sb.append(STR_FAINT_TAG);
        }
        return sb.toString();
    }

    /**
     * Checks if a Pokemon is fainted (has 0 HP)
     * @return Whether if a Pokemon is fainted.
     */
    public boolean isFainted() {
        return currentHp == 0;
    }

    /**
     * Gets the Ii of the Pokemon
     * @return The unique ID of the Pokemon
     */
    public String getId() {
        return this.id;
    }
}