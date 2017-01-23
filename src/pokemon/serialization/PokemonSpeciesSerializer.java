package pokemon.serialization;

import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import serialization.srsf.KeyValuePair;
import serialization.srsf.Lazy;
import serialization.srsf.SerializationContext;
import serialization.srsf.Serializer;

import java.util.HashMap;


public class PokemonSpeciesSerializer extends Serializer<PokemonSpecies> {

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
        //number
        //name primaryType secondaryType weight evolution preEvolution

        int number = keyValuePairs.get("$number").asInt();
        String name = keyValuePairs.get("$name").asString();
        String primaryType = keyValuePairs.get("$primaryType").asString();
        String secondaryType = keyValuePairs.get("$secondaryType").asString();
        double weight = keyValuePairs.get("$weight").asDouble();
        int evolution = keyValuePairs.get("$evolution").asInt();
        int preEvolution = keyValuePairs.get("$preEvolution").asInt();
        return new PokemonSpecies(name, number,
                new Lazy<>(new PokemonTypeResolver(this.getContext(), primaryType)),
                new Lazy<>(new PokemonTypeResolver(this.getContext(), secondaryType)),
                weight,
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), evolution)),
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), preEvolution)));
    }


    /**
     * PokemonSpecies can not be serialized, and are read only.
     * @throws UnsupportedOperationException
     */
    @Override
    public HashMap<String, String> serialize(PokemonSpecies pokemonType) {
        throw new UnsupportedOperationException("Type is static and can not be serialized.");
    }
}
