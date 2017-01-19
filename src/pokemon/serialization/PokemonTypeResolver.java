package pokemon.serialization;
import pokemon.data.PokemonType;
import serialization.srsf.*;

public class PokemonTypeResolver implements LazyResolver<PokemonType> {
    private final SerializationContext context;
    private final String name;

    public PokemonTypeResolver(SerializationContext context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public PokemonType resolve() {
        for (PokemonType p : this.context.getCollection(PokemonType.class)) {
            if (p.getName().equalsIgnoreCase(this.name)) return p;
        }
        return null;
    }
}
