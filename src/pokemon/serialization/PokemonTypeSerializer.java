package pokemon.serialization;

import pokemon.data.PokemonType;
import serialization.srsf.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PokemonTypeSerializer extends Serializer<PokemonType> {
    public PokemonTypeSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonType deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        String name = keyValuePairs.get("$name").asString();
        String[] weakness = keyValuePairs.get("$weakAgainst").asStringArray();
        String[] strength = keyValuePairs.get("$strongAgainst").asStringArray();
        String[] immune = keyValuePairs.get("$immuneAgainst").asStringArray();

        List<Lazy<PokemonType>> weaknesses = new ArrayList<>();
        for (String wkString : weakness) {
            weaknesses.add(new Lazy<>(new PokemonTypeResolver(this.getContext(), wkString)));
        }

        List<Lazy<PokemonType>> strengths = new ArrayList<>();
        for (String strString : strength) {
            strengths.add(new Lazy<>(new PokemonTypeResolver(this.getContext(), strString)));
        }

        List<Lazy<PokemonType>> immunities = new ArrayList<>();
        for (String imString : immune) {
            immunities.add(new Lazy<>(new PokemonTypeResolver(this.getContext(), imString)));
        }
        return new PokemonType(name, weaknesses, strengths, immunities);
    }

    @Override
    public HashMap<String, String> serialize(PokemonType pokemonType) {
        throw new UnsupportedOperationException("Type is static and can not be serialized.");
    }
}
