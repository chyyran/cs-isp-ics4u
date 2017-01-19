package pokemon.serialization;

import pokemon.data.PokemonMove;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonMoveResolver implements LazyResolver<PokemonMove> {
    private final SerializationContext context;
    private final String name;

    public PokemonMoveResolver(SerializationContext context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public PokemonMove resolve() {
        for (PokemonMove move : this.context.getCollection(PokemonMove.class)) {
            if (move.getName().equalsIgnoreCase(this.name)) return move;
        }
        return null;
    }
}
