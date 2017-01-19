package pokemon.serialization;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

public class PokemonIdResolver implements LazyResolver<Pokemon> {
    private final SerializationContext context;
    private final String id;

    public PokemonIdResolver(SerializationContext context, String name) {
        this.context = context;
        this.id = name;
    }

    @Override
    public Pokemon resolve() {
        for (Pokemon pokemon : this.context.getCollection(Pokemon.class)) {
            if (pokemon.getId().equalsIgnoreCase(this.id)) return pokemon;
        }
        return null;
    }
}
