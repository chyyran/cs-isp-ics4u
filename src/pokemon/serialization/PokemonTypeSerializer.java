package pokemon.serialization;

import pokemon.data.PokemonType;
import serialization.srsf.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonTypeSerializer extends Serializer<PokemonType> {
    public PokemonTypeSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonType toObject(HashMap<String, KeyValuePair> keyValuePairs) {
        String name = keyValuePairs.get("$name").asString();
        String[] weakness = keyValuePairs.get("$weakAgainst").asStringArray();
        String[] strength = keyValuePairs.get("$strongAgainst").asStringArray();

        List<Lazy<PokemonType>> weaknesses = new ArrayList<>();
        for (String wkString : weakness) {
            weaknesses.add(new Lazy<>(new PokemonTypeResolver(this.getContext(), wkString)));
        }

        List<Lazy<PokemonType>> strengths = new ArrayList<>();
        for (String strString : strength) {
            strengths.add(new Lazy<>(new PokemonTypeResolver(this.getContext(), strString)));
        }
        return new PokemonType(name, weaknesses, strengths);
    }
}
