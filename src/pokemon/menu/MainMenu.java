package pokemon.menu;

import menu.text.MenuBuilder;
import pokemon.data.*;
import pokemon.serialization.*;
import serialization.srsf.Schema;
import serialization.srsf.SchemaSerializer;
import serialization.srsf.SerializationContext;

import java.io.IOException;

public class MainMenu {
    public static void main(String[] args) {
        SerializationContext sc = new SerializationContext("c:\\srsf");
        sc.addSerializer(new SchemaSerializer(sc), Schema.class);
        sc.addSerializer(new PokemonTypeSerializer(sc), PokemonType.class);
        sc.addSerializer(new PokemonMoveSerializer(sc), PokemonMove.class);
        sc.addSerializer(new PokemonSpeciesSerializer(sc), PokemonSpecies.class);
        sc.addSerializer(new PokemonSerializer(sc), Pokemon.class);
        sc.addSerializer(new PokemonTeamSerializer(sc), PokemonTeam.class);
        try {
            sc.loadCollection(Schema.class);
            sc.loadCollection(PokemonType.class);
            sc.loadCollection(PokemonMove.class);
            sc.loadCollection(PokemonSpecies.class);
            sc.loadCollection(Pokemon.class);
            sc.loadCollection(PokemonTeam.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuBuilder menuBuilder = new MenuBuilder();
        menuBuilder.option(new PokedexMenu(sc.getCollection(PokemonSpecies.class)));
        menuBuilder.run();
    }
}
