package pokemon.menu;

import pokemon.data.*;
import pokemon.serialization.PokemonMoveSerializer;
import pokemon.serialization.PokemonSpeciesSerializer;
import pokemon.serialization.PokemonTypeSerializer;
import serialization.srsf.Schema;
import serialization.srsf.SchemaSerializer;
import serialization.srsf.SerializationContext;

import java.io.IOException;

/**
 * Created by Ronny on 2017-01-17.
 */
public class MainMenu {
    public static void main(String[] args) {
        SerializationContext sc = new SerializationContext("c:\\srsf");
        sc.addSerializer(new PokemonTypeSerializer(sc), PokemonType.class);
        sc.addSerializer(new PokemonMoveSerializer(sc), PokemonMove.class);
        sc.addSerializer(new PokemonSpeciesSerializer(sc), PokemonSpecies.class);
        sc.addSerializer(new SchemaSerializer(sc), Schema.class);
        try {
            sc.loadCollection("schema", Schema.class);
            sc.loadCollection("PokemonType", PokemonType.class);
            sc.loadCollection("PokemonMove", PokemonMove.class);
            sc.loadCollection("PokemonSpecies", PokemonSpecies.class);
            sc.loadCollection("Pokemon", Pokemon.class);
            sc.loadCollection("PokemonTeam", PokemonTeam.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Schema s : sc.getCollection(Schema.class)) {
            System.out.println(s);
        }

        for (PokemonType t : sc.getCollection(PokemonType.class)) {
            System.out.println(t.getWeaknesses().get(0).getName());
        }
    }
}
