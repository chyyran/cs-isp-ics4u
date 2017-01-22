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
 * Serializes a Pokemon from an SRSF block
 */
public class PokemonSerializer extends Serializer<Pokemon> {

    /**
     * Instantiates the PokemonSerializer into the given context
     * @param context The serialization context this serializer will be loaded into
     */
    public PokemonSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public Pokemon deserialize(HashMap<String, KeyValuePair> keyValuePairs) {
        int species = keyValuePairs.get("$species").asInt();
        String nickName = keyValuePairs.get("$nickname").asString();
        int level = keyValuePairs.get("$level").asInt();
        String[] moveStr = keyValuePairs.get("$moves").asStringArray();
        int hp = keyValuePairs.get("$hp").asInt();
        String id = keyValuePairs.get("$id").asString();

        List<Lazy<PokemonMove>> moves = new ArrayList<>();
        for (String move : moveStr) {
            moves.add(new Lazy<>(new PokemonMoveResolver(this.getContext(), move)));
        }

        return new Pokemon(id,
                new Lazy<>(new PokemonSpeciesResolver(this.getContext(), species)),
                moves,
                nickName,
                level,
                hp);
    }

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
