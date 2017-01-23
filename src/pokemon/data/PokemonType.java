package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * The type of a Pokemon species or move.
 */
public class PokemonType {
    /**
     * The name of the Pokemon type
     */
    private final String name;
    /**
     * The to-be-resolved list of weaknesses of this type
     */
    private final List<Lazy<PokemonType>> weaknesses;
    /**
     * The to-be-resolved list of strengths of this type
     */
    private final List<Lazy<PokemonType>> strengths;
    /**
     * The to-be-resolved list of immunities of this type.
     */
    private final List<Lazy<PokemonType>> immunities;

    /**
     * Instantiates a PokemonType
     * @param name The name of the type
     * @param weaknesses The weaknesses this type has
     * @param strengths The strengths this type has
     * @param immunities The immunities this type has
     */
    public PokemonType(String name, List<Lazy<PokemonType>> weaknesses,
                       List<Lazy<PokemonType>> strengths, List<Lazy<PokemonType>> immunities) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
        this.immunities = immunities;
    }

    /**
     * Gets the name of the type
     * @return The name of the type
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the string representation of the type
     * @return The name of the type
     */
    @Override
    public String toString() {
        return this.getName();
    }

    /**
     * Gets an immutable copy of the weaknesses of this type
     * @return The weaknesses of this type
     */
    public List<PokemonType> getWeaknesses() {
        // this needs caching
        List<PokemonType> types = Lazy.asList(this.weaknesses);
        return types;
    }

    /**
     * Gets an immutable copy of the strengths of this type
     * @return The strengths of this type
     */
    public List<PokemonType> getStrengths() {
        //also needs caching.
        List<PokemonType> types = Lazy.asList(this.strengths);
        return types;
    }

    /**
     * Gets an immutable copy of the immunities of this type
     * @return The immunities of this type
     */
    public List<PokemonType> getImmunities() {
        //also needs caching.
        List<PokemonType> types = Lazy.asList(this.immunities);
        return types;
    }

    /**
     * Checks if a type exists in a list of types
     * @param type The type to search for
     * @param types The list of lazily-wrapped types to search in
     * @return Whether or not the type exists
     */
    private static boolean containsType(PokemonType type, List<Lazy<PokemonType>> types) {
        for(Lazy<PokemonType> _type : types) {
            if(_type.getValue() == null) continue;
            if(_type.getValue().equals(type)) return true;
        }
        return false;
    }

    /**
     * Returns if this type is immune against the given type
     * @param type The type to check against
     * @return Whether if this type is immune against the given type
     */
    public boolean isImmuneAgainst(PokemonType type) {
       return containsType(type, this.immunities);
    }

    /**
     * Returns if this type is strong against the given type
     * @param type The type to check against
     * @return Whether if this type is strong against the given type
     */
    public boolean isStrongAgainst(PokemonType type) {
        return containsType(type, this.strengths);
    }

    /**
     * Returns if this type is weak against the given type
     * @param type The type to check against
     * @return Whether if this type is weakagainst the given type
     */
    public boolean isWeakAgainst(PokemonType type) {
        return containsType(type, this.weaknesses);
    }

    /**
     * Checks if two types are equal by their names
     * @param type The type to check
     * @return Two types are equal if their names match.
     */
    public boolean equals(PokemonType type) {
        return type != null && this.getName().equalsIgnoreCase(type.getName());
    }

    /**
     * Checks if two types are equal by their names, if the given object is
     * a PokemonType
     * @param o The object to check
     * @return Two types are equal if their names match.
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof PokemonType && equals((PokemonType) o);
    }
}
