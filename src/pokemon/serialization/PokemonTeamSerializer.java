package pokemon.serialization;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonTeam;
import serialization.srsf.KeyValuePair;
import serialization.srsf.Lazy;
import serialization.srsf.SerializationContext;
import serialization.srsf.Serializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PokemonTeamSerializer extends Serializer<PokemonTeam> {
    public PokemonTeamSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public PokemonTeam deserialize(HashMap<String, KeyValuePair> keyValuePairs) {

        String[] pokemonTeam = keyValuePairs.get("$pokemon").asStringArray();

        ArrayList<Lazy<Pokemon>> team = new ArrayList<>();
        for (String pokemon : pokemonTeam) {
            team.add(new Lazy<>(new PokemonIdResolver(this.getContext(), pokemon)));
        }

        return new PokemonTeam(team);
    }

    @Override
    public HashMap<String, String> serialize(PokemonTeam team) {
        HashMap<String, String> values = new HashMap<>();
        List<Pokemon> pokemon = team.getPokemon();
        String[] pokemonids = new String[pokemon.size()];
        for (int i = 0; i < pokemonids.length; i++) {
            if(pokemon.get(i) != null) {
                pokemonids[i] = pokemon.get(i).getId();
            }else{
                pokemonids[i] = "@@NUL@@";
            }
        }
        values.put("$pokemon", Serializer.arrayFormat(pokemonids));
        return values;
    }
}
