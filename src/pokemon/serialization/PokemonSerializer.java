package pokemon.serialization;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import serialization.srsf.KeyValuePair;
import serialization.srsf.Lazy;
import serialization.srsf.SerializationContext;
import serialization.srsf.Serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Serializes/Deserializes a Pokemon from an SRSF block
 */
public class PokemonSerializer extends Serializer<Pokemon> {

    /**
     * Instantiates the PokemonSerializer into the given context
     * @param context The serialization context this serializer will be loaded into
     */
    public PokemonSerializer(SerializationContext context) {
        super(context);
    }

    /**
     * Converts an SRSF block into a Pokemon object
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The deserialized Pokemon object
     */
    @Override
    public Pokemon deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        int species = keyValuePairs.get("$species").asInt();
        String nickName = keyValuePairs.get("$nickname").asString();
        int level = keyValuePairs.get("$level").asInt();
        String[] moveStr = keyValuePairs.get("$moves").asStringArray();
        int hp = keyValuePairs.get("$hp").asInt();
        String id = keyValuePairs.get("$id").asString(); //get data from the hashmap

        List<Lazy<PokemonMove>> moves = new ArrayList<>();
        for (String move : moveStr) {
            moves.add(new Lazy<>(new PokemonMoveResolver(this.getContext(), move))); //create a lazy resolver for
                                                                // each move, that resolves the move from the name
        }

        return new Pokemon(id,
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), species)), //creates a lazy resolver for the
                                                                            // species, resolving it from the number
                moves,
                nickName,
                level,
                hp);
    }

    /**
     * Converts a Pokemon object into SRSF String Key-value pairs
     * @param pokemon The Pokemon to convert
     * @return The string key value pairs
     */
    @Override
    public HashMap<String, String> serialize(Pokemon pokemon) {
        if(pokemon == null) return null;
        HashMap<String, String> values = new HashMap<>();
        values.put("$species", String.valueOf(pokemon.getSpecies().getNumber()));
        values.put("$id", pokemon.getId());
        values.put("$nickname", pokemon.getNickname());
        values.put("$level", String.valueOf(pokemon.getLevel()));
        String[] moveNames = new String[pokemon.getMoves().size()];
        for (int i = 0; i < moveNames.length; i++) {
            moveNames[i] = pokemon.getMoves().get(i).getName();
        }
        values.put("$moves", Serializer.arrayFormat(moveNames));
        values.put("$hp", String.valueOf(pokemon.getCurrentHp()));
        return values;
    }
}
