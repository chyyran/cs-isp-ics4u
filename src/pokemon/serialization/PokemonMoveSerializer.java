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

/**
 * Serializes/Deserializes a PokemonMove from an SRSF block
 */
public class PokemonMoveSerializer extends Serializer<PokemonMove> {
    /**
     * Instantiates the PokemonMoveSerializer into the given context
     * @param context The serialization context this serializer will be loaded into
     */
    public PokemonMoveSerializer(SerializationContext context) {
        super(context);
    }

    /**
     * Converts an SRSF block into a PokemonMove object
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The deserialized PokemonMove object
     */
    @Override
    public PokemonMove deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        String name = keyValuePairs.get("$name").asString();
        String type = keyValuePairs.get("$type").asString(); //get the type as it's name
        double baseDamage = keyValuePairs.get("$baseDamage").asDouble();
        double selfDamage = keyValuePairs.get("$selfDamage").asDouble(); //gets the value
        return new PokemonMove(name, new Lazy<>(new PokemonTypeResolver(this.getContext(), type)), baseDamage, selfDamage);
            //instantiates the PokemonMove, with the type of the move as a Lazy-wrapped value resolving from the
            //type name
    }

    /**
     * PokemonMoves can not be serialized, and are read only.
     * @throws UnsupportedOperationException
     */
    @Override
    public HashMap<String, String> serialize(PokemonMove pokemonType) {
        throw new UnsupportedOperationException("Type is static and can not be serialized.");
    }
}
