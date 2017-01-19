package pokemon.menu;

import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonTeam;
import serialization.srsf.Lazy;
import serialization.srsf.LazyResolver;

import java.util.*;

public class TeamMenu extends MenuOption {
    private PokemonTeam team;
    private final MenuBuilder teamMenu;

    public TeamMenu(final PokemonTeam _team, final List<PokemonMove> moves, final List<PokemonSpecies> pokemonSpecies) {
        super("Team Manager");
        this.team = _team;
        this.teamMenu = new MenuBuilder();
        this.teamMenu
                .option(new MenuOption("Make or Edit Team") {
                    @Override
                    public void run() {
                        int selection = 0;
                        do {
                            Scanner scanner = new Scanner(System.in);
                            if (team == null) team = new PokemonTeam();
                            System.out.println(team);
                            System.out.println("Pick a slot.");
                            selection = Integer.parseInt(scanner.nextLine()); //todo: validate input.
                            System.out.println("Pick a pokemon.");
                            int pokemonNumber = Integer.parseInt(scanner.nextLine()); //todo: validate input.
                            PokemonSpecies pokemon = null;
                            for (PokemonSpecies p : pokemonSpecies) {
                                if (p.getNumber() == pokemonNumber) pokemon = p; //this should be in pokedex
                            }
                            final PokemonSpecies _pokemon = pokemon; //finalize pokemon
                            Pokemon p = new Pokemon(UUID.randomUUID().toString(), new Lazy<PokemonSpecies>(new LazyResolver<PokemonSpecies>() {
                                @Override
                                public PokemonSpecies resolve() {
                                    return _pokemon; //todo: make this easier.
                                }
                            }), getFourMoves(moves), _pokemon.getName(), 1);
                            team.setPokemon(selection - 1, p);
                            //this should ideally be done with a MenuOption, but in the interest of time.
                        } while (selection > 0);
                    }
                })
                .option(new MenuOption("View Team") {
                    @Override
                    public void run() {
                        if (team != null) {
                            System.out.print(team);
                        } else {
                            System.out.print("You don't have a team!");
                        }

                    }
                });
    }

    private static List<Lazy<PokemonMove>> getFourMoves(List<PokemonMove> validMoves) {
        List<PokemonMove> copy = new LinkedList<>(validMoves);
        Collections.shuffle(copy);
        List<Lazy<PokemonMove>> pokemonMove = new ArrayList<>();
        for (PokemonMove move : copy.subList(0, 4)) {
            final PokemonMove _move = move; //got to get this done.
            pokemonMove.add(new Lazy<PokemonMove>(new LazyResolver<PokemonMove>() {
                @Override
                public PokemonMove resolve() {
                    return _move;
                }
            }));
        }
        return pokemonMove;
    }

    @Override
    public void run() {
        this.teamMenu.run();
    }
}
