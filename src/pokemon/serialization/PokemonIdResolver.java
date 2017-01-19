package pokemon.serialization;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonIdResolver implements LazyResolver<Pokemon> {
    private final SerializationContext context;
    private final String id;

    public PokemonIdResolver(SerializationContext context, String name) {
        this.context = context;
        this.id = name;
    }

    @Override
    public Pokemon resolve() {
        for (Pokemon move : this.context.getCollection(Pokemon.class)) {
            if (move.getId().equalsIgnoreCase(this.id)) return move;
        }
        return null;
    }
}
