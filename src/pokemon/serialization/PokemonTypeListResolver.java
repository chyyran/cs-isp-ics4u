package serialization.srsf;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonTypeListResolver implements LazyResolver<List<PokemonType>>
{
    private final SerializationContext context;
    private final String[] names;
    public PokemonTypeListResolver(SerializationContext context, String... names) {
        this.context = context;
        this.names = names;
    }

    @Override
    public List<PokemonType> resolve()
    {
        List<PokemonType> types = new ArrayList<>();
        for(PokemonType p : this.context.getCollection(PokemonType.class)) {
            for(String t : names) {
                if(p.getName().equals(t)) types.add(p);
            }
        }
        return types;
    }
}
