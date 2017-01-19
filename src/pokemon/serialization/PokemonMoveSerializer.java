package pokemon.serialization;

import pokemon.data.PokemonMove;
import pokemon.data.PokemonType;
import serialization.srsf.KeyValuePair;
import serialization.srsf.Lazy;
import serialization.srsf.SerializationContext;
import serialization.srsf.Serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PokemonMoveSerializer extends Serializer<PokemonMove> {
    public PokemonMoveSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonMove deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        String name = keyValuePairs.get("$name").asString();
        String type = keyValuePairs.get("$type").asString();
        double baseDamage = keyValuePairs.get("$baseDamage").asDouble();
        double selfDamage = keyValuePairs.get("$selfDamage").asDouble();
        return new PokemonMove(name, new Lazy<>(new PokemonTypeResolver(this.getContext(), type)), baseDamage, selfDamage);
    }

    @Override
    public HashMap<String, String> serialize(PokemonMove pokemonType) {
        throw new UnsupportedOperationException("Type is static and can not be serialized.");
    }
}
