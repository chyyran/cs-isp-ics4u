package serialization.srsf;

import pokemon.data.PokemonType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonTypeResolver implements LazyResolver<PokemonType>
{
    private final SerializationContext context;
    private final String name;
    public PokemonTypeResolver(SerializationContext context, String name) {
        this.context = context;
        this.name = name;
    }

    @Override
    public PokemonType resolve()
    {
        for(PokemonType p : this.context.getCollection(PokemonType.class)) {
            if(p.getName().equals(this.name)) return p;
        }
        return null;
    }
}
