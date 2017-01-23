package pokemon.serialization;

import pokemon.data.PokemonSpecies;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

/**
 * A lazy resolver to resolve a PokemonSpecies with a given unique ID from a loaded
 * serialization context
 * @see PokemonSpecies
 */
public class PokemonSpeciesResolver implements LazyResolver<PokemonSpecies> {
    /**
     * The serialization context to search from
     */
    private final SerializationContext context;
    /**
     * The unique number of the PokemonSpecies
     */
    private final int number;

    /**
     * Instantiates the resolver
     * @param context The context the desired PokemonSpecies is contained in
     * @param number The name of the desired PokemonSpecies
     */
    public PokemonSpeciesResolver(SerializationContext context, int number) {
        this.context = context;
        this.number = number;
    }

    /**
     * Searches for an resolves the desired PokemonMove
     * @return The desired PokemonMove, or null if it does not exist.
     */
    @Override
    public PokemonSpecies resolve() {
        for (PokemonSpecies species : this.context.getCollection(PokemonSpecies.class)) {
            if (species.getNumber() == this.number) return species;
        }
        return null;
    }
}
