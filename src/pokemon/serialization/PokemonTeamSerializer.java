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

/**
 * Serializes/Deserializes a Pokemon from an SRSF block
 */
public class PokemonTeamSerializer extends Serializer<PokemonTeam> {

    /**
     * Key for the Pokemon array for this team
     */
    public static final String $_POKEMON = "$pokemon";

    /**
     * Instantiates the resolver
     * @param context The context the desired PokemonTeam is contained in
     */
    public PokemonTeamSerializer(SerializationContext context) {
        super(context);
    }


    /**
     * Converts an SRSF block into a PokemonTeam object
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The deserialized PokemonTeam object
     */
    @Override
    public PokemonTeam deserialize(HashMap<String, KeyValuePair> keyValuePairs) {

        String[] pokemonTeam = keyValuePairs.get($_POKEMON).asStringArray(); //get pokemon as an array of IDs
        ArrayList<Lazy<Pokemon>> team = new ArrayList<>();
        for (String pokemon : pokemonTeam) {
            team.add(new Lazy<>(new PokemonIdResolver(this.getContext(), pokemon))); //make an array of Lazy<Pokemon>, resolved by their ID
        }
        return new PokemonTeam(team);
    }

    /**
     * Converts a Pokemon object into SRSF String Key-value pairs
     * @param team The PokemonTeam to convert
     * @return The string key value pairs
     */
    @Override
    public HashMap<String, String> serialize(PokemonTeam team) {
        HashMap<String, String> values = new HashMap<>();
        List<Pokemon> pokemon = team.getPokemon();
        String[] pokemonids = new String[pokemon.size()];
        for (int i = 0; i < pokemonids.length; i++) { //loop through all the IDs and put them into the string array
            if(pokemon.get(i) != null) {
                pokemonids[i] = pokemon.get(i).getId();
            }else{
                pokemonids[i] = "@@NUL@@";
            }
        }
        values.put($_POKEMON, Serializer.arrayFormat(pokemonids)); //serialize the string array
        return values;
    }
}
