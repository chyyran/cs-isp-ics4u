package pokemon.serialization;

import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import serialization.srsf.KeyValuePair;
import serialization.srsf.Lazy;
import serialization.srsf.SerializationContext;
import serialization.srsf.Serializer;

import java.util.HashMap;

/**
 * Serializes/Deserializes a PokemonSpecies from an SRSF block
 */
public class PokemonSpeciesSerializer extends Serializer<PokemonSpecies> {

    /**
     * The key for the Pokemon species number
     */
    public static final String $_NUMBER = "$number";
    /**
     * The key for the Pokemon species name
     */
    public static final String $_NAME = "$name";
    /**
     * The key for the primary type of the Pokemon
     */
    public static final String $_PRIMARY_TYPE = "$primaryType";
    /**
     * The key for the secondary type of the Pokemon
     */
    public static final String $_SECONDARY_TYPE = "$secondaryType";
    /**
     * The key for the weight of the Pokemon
     */
    public static final String $_WEIGHT = "$weight";
    /**
     * The key for the evolution of the Pokemon
     */
    public static final String $_EVOLUTION = "$evolution";
    /**
     * The key for the pre evolution of the Pokemon
     */
    public static final String $_PRE_EVOLUTION = "$preEvolution";

    /**
     * Instantiates the PokemonSpeciesSerializer into the given context
     * @param context The serialization context this serializer will be loaded into
     */
    public PokemonSpeciesSerializer(SerializationContext context) {
        super(context);
    }

    /**
     * Converts an SRSF block into a PokemonSpecies object
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The deserialized PokemonSpecies object
     */
    @Override
    public PokemonSpecies deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        int number = keyValuePairs.get($_NUMBER).asInt(); //gets the species number
        String name = keyValuePairs.get($_NAME).asString(); //gets the species name
        String primaryType = keyValuePairs.get($_PRIMARY_TYPE).asString(); //gets the primary type name
        String secondaryType = keyValuePairs.get($_SECONDARY_TYPE).asString(); //gets the secondary type name
        double weight = keyValuePairs.get($_WEIGHT).asDouble(); //get the weight
        int evolution = keyValuePairs.get($_EVOLUTION).asInt(); //get the pokemon this evolves to by their number
        int preEvolution = keyValuePairs.get($_PRE_EVOLUTION).asInt(); //get the pokemon this evolves from by their number
        return new PokemonSpecies(name, number,
                new Lazy<>(new PokemonTypeResolver(this.getContext(), primaryType)),
                new Lazy<>(new PokemonTypeResolver(this.getContext(), secondaryType)),
                //Gets the types as a Lazy<PokemonType>, resolving by the type name
                weight,
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), evolution)),
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), preEvolution)));
                //Gets the (pre)evolutions as a Lazy<PokemonSpecies>, resolving by the pokemon number.
    }


    /**
     * PokemonSpecies can not be serialized, and are read only.
     * @throws UnsupportedOperationException
     */
    @Override
    public HashMap<String, String> serialize(PokemonSpecies pokemonType) {
        throw new UnsupportedOperationException("Species are is static and can not be serialized.");
    }
}
