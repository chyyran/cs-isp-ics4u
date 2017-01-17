package serialization.srsf;

import java.util.HashMap;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonTypeSerializer extends Serializer<PokemonType>
{
    public PokemonTypeSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonType toObject(HashMap<String, KeyValuePair> keyValuePairs)
    {
        String name = keyValuePairs.get("$name").asString();
        String[] weakness = keyValuePairs.get("$weakAgainst").asStringArray();
        String[] strength = keyValuePairs.get("$strongAgainst").asStringArray();

        return new PokemonType(name, new Lazy<>(new PokemonTypeListResolver(this.getContext(), weakness)),
                new Lazy<>(new PokemonTypeListResolver(this.getContext(), strength)));
}
}
