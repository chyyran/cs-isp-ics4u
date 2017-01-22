package pokemon.serialization;
import pokemon.data.PokemonType;
import serialization.srsf.*;

/**
 * A lazy resolver to resolve a PokemonType with the given name from a loaded
 * serialization context
 * @see PokemonType
 */
public class PokemonTypeResolver implements LazyResolver<PokemonType> {
    /**
     * The serialization context to search from
     */
    private final SerializationContext context;

    /**
     * The name of the desired PokemonType
     */
    private final String name;

    /**
     * Instantiates the resolver
     * @param context The context the desired PokemonType is contained in
     * @param name The name of the desired PokemonType
     */
    public PokemonTypeResolver(SerializationContext context, String name) {
        this.context = context;
        this.name = name;
    }

    /**
     * Resolves the desired PokemonType from it's name
     * @return The desiered PokemonType, or null if it does not exist.
     */
    @Override
    public PokemonType resolve() {
        for (PokemonType p : this.context.getCollection(PokemonType.class)) {
            if (p.getName().equalsIgnoreCase(this.name)) return p;
        }
        return null;
    }
}
