package pokemon.menu;

import menu.text.*;
import pokemon.data.*;
import pokemon.serialization.*;
import serialization.srsf.Lazy;
import serialization.srsf.Schema;
import serialization.srsf.SchemaSerializer;
import serialization.srsf.SerializationContext;

import java.io.IOException;
import java.util.List;

public class MainMenu {

    /**
     * The data directory
     */
    public static final String DATA_DIRECTORY = "data";

    public static void main(String[] args) {

        //instantiate the serialization context in the data folder
        final SerializationContext sc = new SerializationContext(DATA_DIRECTORY);
        //Add the serializers for each class
        sc.addSerializer(new SchemaSerializer(sc), Schema.class);
        sc.addSerializer(new PokemonTypeSerializer(sc), PokemonType.class);
        sc.addSerializer(new PokemonMoveSerializer(sc), PokemonMove.class);
        sc.addSerializer(new PokemonSpeciesSerializer(sc), PokemonSpecies.class);
        sc.addSerializer(new PokemonSerializer(sc), Pokemon.class);
        sc.addSerializer(new PokemonTeamSerializer(sc), PokemonTeam.class);

        //Attempt to load the data files into the serialization context
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

        //Instantiate the root menu
        MenuBuilder menuBuilder = new MenuBuilder();

        //Get the team from the loaded data, or create one if it does not exist
        List<PokemonTeam> teamArray = sc.getCollection(PokemonTeam.class);
        if (teamArray.isEmpty()) teamArray.add(new PokemonTeam());
        final PokemonTeam team = teamArray.get(0);

        //Instantiate the Pokedex object
        final Pokedex pokedex = new Pokedex(sc.getCollection(PokemonSpecies.class));

        //Build the root menu
        menuBuilder.option(new PokedexMenu(pokedex, sc.getCollection(PokemonType.class)))
                .option(new TeamMenu(team, sc.getCollection(PokemonMove.class),
                        pokedex))
                .option(new BattleMenu(team, sc.getCollection(PokemonSpecies.class),
                        sc.getCollection(PokemonMove.class)));

        //Save the data files on application exit
        menuBuilder.exit(new MenuOption("Save on exit") {
            @Override
            public void run() {
                try {
                    //Cache the Pokemon
                    List<Pokemon> pokemon = team.getPokemon();

                    //We only ever want one PokemonTeam, and one set of Pokemon, to be saved.
                    //Here we clear out the old data
                    sc.getCollection(Pokemon.class).clear();
                    sc.getCollection(Pokemon.class).addAll(pokemon);
                    sc.getCollection(PokemonTeam.class).set(0, team);

                    //Save the Pokemon
                    sc.saveCollection(Pokemon.class);
                    sc.saveCollection(PokemonTeam.class);
                    System.out.println("Goodbye!");
                }catch(IOException e) {
                    e.printStackTrace();
                    System.out.println("We were unable to save your changes.");
                }
            }
        }).error(new ErrorHandler() {
            @Override
            public void handle(Exception e) {
               System.out.println ("There was a general error");
            }
        });
        //Start the main application loop
        menuBuilder.run();
    }
}
