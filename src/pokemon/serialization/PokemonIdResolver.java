package pokemon.serialization;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

/**
 * A lazy resolver to resolve a pokemon with a given unique ID from a loaded
 * serialization context
 * @see Pokemon
 */
public class PokemonIdResolver implements LazyResolver<Pokemon> {
    /**
     * The serialization context to search from
     */
    private final SerializationContext context;
    /**
     * The String id of the desired Pokemon
     */
    private final String id;

    /**
     * Instantiates the resolver
     * @param context The context the desired Pokemon is contained in
     * @param id The id of the desired Pokemon
     */
    public PokemonIdResolver(SerializationContext context, String id) {
        this.context = context;
        this.id = id;
    }

    /**
     * Resolves the desired Pokemon by looking through the context.
     * @return The desired Pokemon with the given ID. Null if it does not exist.
     */
    @Override
    public Pokemon resolve() {
        for (Pokemon pokemon : this.context.getCollection(Pokemon.class)) {
            if(pokemon == null) continue;
            if (pokemon.getId().equalsIgnoreCase(this.id)) return pokemon;
        }
        return null;
    }
}
