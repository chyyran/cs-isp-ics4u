package pokemon.serialization;

import pokemon.data.PokemonMove;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;
/**
 * A lazy resolver to resolve a Pokemon move from it's name from a loaded
 * serialization context
 * @see PokemonMove
 */
public class PokemonMoveResolver implements LazyResolver<PokemonMove> {
    /**
     * The serialization context to search from
     */
    private final SerializationContext context;
    /**
     * The name of the PokemonMove
     */
    private final String name;

    /**
     * Instantiates the resolver
     * @param context The context the desired PokemonMove is contained in
     * @param name The name of the desired PokemonMove
     */
    public PokemonMoveResolver(SerializationContext context, String name) {
        this.context = context;
        this.name = name;
    }

    /**
     * Searches for an resolves the desired PokemonMove
     * @return The desired PokemonMove, or null if it does not exist.
     */
    @Override
    public PokemonMove resolve() {
        for (PokemonMove move : this.context.getCollection(PokemonMove.class)) {
            if (move.getName().equalsIgnoreCase(this.name)) return move;
        }
        return null;
    }
}
