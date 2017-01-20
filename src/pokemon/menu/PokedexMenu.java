package pokemon.menu;

import menu.text.ErrorHandler;
import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.data.Pokemon;
import pokemon.data.PokemonSpecies;

import java.util.List;
import java.util.Scanner;

public class PokedexMenu extends MenuOption {

    private final MenuBuilder pokedexMenu;
    private final MenuBuilder searchMenu;
    private final List<PokemonSpecies> pokemon;

    protected PokedexMenu(final List<PokemonSpecies> pokemon) {
        super("Pokedex");
        this.pokemon = pokemon;
        final Scanner sc = new Scanner(System.in);
        this.searchMenu = new MenuBuilder();
        this.searchMenu
                .option(new MenuOption("Search by Name") {
                    @Override
                    public void run() {
                        System.out.println("Please enter a name: ");
                        String searchQuery = sc.nextLine();
                    }
                })
                .option(new MenuOption("Search by ID") {
                    @Override
                    public void run() {
                        System.out.println("Please enter an ID: ");
                        String searchQuery = sc.nextLine();
                    }
                })
                .option(new MenuOption("Search by Type") {
                    @Override
                    public void run() {
                        System.out.println("Please enter a Type: ");
                        String searchQuery = sc.nextLine();
                    }
                }).error(new ErrorHandler() {
            @Override
            public void handle(Exception e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("Invalid ID. Press enter to go back.");
                }
            }
        });

        this.pokedexMenu = new MenuBuilder();
        this.pokedexMenu
                .option(new MenuOption("Search for Pokemon") {
                    @Override
                    public void run() {
                        searchMenu.run();
                    }
                })
                .option(new MenuOption("Sort Pokemon") {
                    @Override
                    public void run() {

                    }
                })
                .option(new MenuOption("Open Pokedex") {
                    @Override
                    public void run() {
                        new PokedexPaginator(pokemon, pokedexMenu).run();
                    }
                });
    }

    @Override
    public void run() {
        this.pokedexMenu.run();
    }

    private class PokedexPaginator extends MenuOption {
        private final List<PokemonSpecies> pokemon;
        private final MenuBuilder menuBuilder;

        public PokedexPaginator(List<PokemonSpecies> pokemon, MenuBuilder initial) {
            super("Pokedex Paginator");
            this.pokemon = pokemon;
            this.menuBuilder = new MenuBuilder();
            menuBuilder.option(new MenuOption("View Results..") {
                @Override
                public void run() {
                    buildMenu(0, 5).run();
                }
            });
        }

        private MenuBuilder buildMenu(final int pokemonStart,
                                      final int length) {
            final MenuBuilder nextFivePokemon = new MenuBuilder();
            for (int i = 0; i < pokemon.size() && i < length; i++) {
                final PokemonSpecies pokemonSpecies = pokemon.get(i + pokemonStart);
                nextFivePokemon.option(new MenuOption(pokemonSpecies.getName()) {
                    @Override
                    public void run() {
                        System.out.println(pokemonSpecies);
                    }
                });
            }
            if (!(pokemonStart + length + 1 > pokemon.size())) {
                nextFivePokemon.option(new MenuOption("Next page") {
                    @Override
                    public void run() {
                        buildMenu(pokemonStart + length, length).run();
                    }
                });
            }

            return nextFivePokemon;


        }

        @Override
        public void run() {
            this.menuBuilder.run();
        }
    }
}
