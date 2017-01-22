package pokemon.menu;

import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.core.Pokedex;
import pokemon.data.*;
import pokemon.serialization.*;
import serialization.srsf.Lazy;
import serialization.srsf.Schema;
import serialization.srsf.SchemaSerializer;
import serialization.srsf.SerializationContext;

import java.io.IOException;
import java.util.List;

public class MainMenu {
    public static void main(String[] args) {
        final SerializationContext sc = new SerializationContext("data");
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
            System.out.println("Unable to load data files. Please ensure all data files are in the data directory.");
            return;
        }

        MenuBuilder menuBuilder = new MenuBuilder();
        List<PokemonTeam> teamArray = sc.getCollection(PokemonTeam.class);
        if (teamArray.isEmpty()) teamArray.add(new PokemonTeam());
        final PokemonTeam team = teamArray.get(0);
        menuBuilder.option(new PokedexMenu(new Pokedex(sc.getCollection(PokemonSpecies.class)),
                sc.getCollection(PokemonType.class)))
                .option(new TeamMenu(team, sc.getCollection(PokemonMove.class),
                        sc.getCollection(PokemonSpecies.class)))
                .option(new BattleMenu(team, sc.getCollection(PokemonSpecies.class),
                        sc.getCollection(PokemonMove.class)));
        menuBuilder.exit(new MenuOption("Save on exit") {
            @Override
            public void run() {
                try {
                    sc.getCollection(Pokemon.class).addAll(team.getPokemon());
                    sc.getCollection(PokemonTeam.class).set(0, team);
                    sc.saveCollection(Pokemon.class);
                    sc.saveCollection(PokemonTeam.class);
                }catch(IOException e) {
                    e.printStackTrace();
                    System.out.println("We were unable to save your changes.");
                }
            }
        });
        menuBuilder.run();
    }
}
