package pokemon.serialization;

import pokemon.data.PokemonType;
import serialization.srsf.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Serializes/Deserializes a PokemonType from an SRSF block
 */
public class PokemonTypeSerializer extends Serializer<PokemonType> {

    /**
     * Key for the name of the type
     */
    public static final String $_NAME = "$name";
    /**
     * Key for the array of weaknesses this type has.
     */
    public static final String $_WEAK_AGAINST = "$weakAgainst";
    /**
     * Key for the array of strengths this type has.
     */
    public static final String $_STRONG_AGAINST = "$strongAgainst";
    /**
     * Key for the array of immunities this type has.
     */
    public static final String $_IMMUNE_AGAINST = "$immuneAgainst";

    /**
     * Instantiates the PokemonSerializer into the given context
     * @param context The serialization context this serializer will be loaded into
     */
    public PokemonTypeSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonType deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        String name = keyValuePairs.get($_NAME).asString();
        String[] weakness = keyValuePairs.get($_WEAK_AGAINST).asStringArray();
        String[] strength = keyValuePairs.get($_STRONG_AGAINST).asStringArray();
        String[] immune = keyValuePairs.get($_IMMUNE_AGAINST).asStringArray();

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
