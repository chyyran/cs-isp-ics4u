package pokemon.menu;

import menu.text.ErrorHandler;
import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.core.Pokedex;
import pokemon.data.Pokemon;
import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonType;

import java.util.List;
import java.util.Scanner;

public class PokedexMenu extends MenuOption {

    private final MenuBuilder pokedexMenu;
    private final MenuBuilder searchMenu;
    private final MenuBuilder sortMenu;

    private final Pokedex pokedex;
    private final List<PokemonType> types;

    protected PokedexMenu(final Pokedex pokedex, final List<PokemonType> types) {
        super("Pokedex");
        this.pokedex = pokedex;
        this.types = types;
        final Scanner sc = new Scanner(System.in);
        this.searchMenu = new MenuBuilder();
        this.searchMenu
                .option(new MenuOption("Search by Name") {
                    @Override
                    public void run() {
                        System.out.println("Please enter a name: ");
                        String searchQuery = sc.nextLine();
                        PokemonSpecies searchResult = pokedex.searchPokemonByName(searchQuery);
                        if(searchResult != null ) {
                            System.out.println(searchResult);
                        }else{
                            System.out.println("That Pokemon was not found. Please check your spelling!");
                        }
                    }
                })
                .option(new MenuOption("Search by ID") {
                    @Override
                    public void run() {
                        System.out.println("Please enter an ID: ");
                        int searchQuery = Integer.parseInt(sc.nextLine());
                        PokemonSpecies searchResult = pokedex.getPokemon(searchQuery);
                        if(searchResult != null ) {
                            System.out.println(searchResult);
                        }else{
                            System.out.println("That Pokemon was not found. There are " + pokedex.getAllPokemon().size()
                                    + " Pokemon registered.");
                        }
                    }
                })
                .option(new MenuOption("Search by Type") {
                    @Override
                    public void run() {
                        System.out.println("Available Pokemon Types: ");
                        int typeSelect = 0;
                        for(int i = 0; i < types.size(); i++) {
                            System.out.println("[" + (i+1) + "] " + types.get(i));
                        }
                        do {
                            try {
                                System.out.println("Please choose a type.");
                                typeSelect = Integer.parseInt(sc.nextLine());
                            }catch(NumberFormatException e) {
                                continue;
                            }
                        }while(typeSelect <= 0 || typeSelect > types.size());
                        PokemonType type = types.get(typeSelect - 1);
                        List<PokemonSpecies> searchResult = pokedex.searchPokemonByType(type);
                        new PokedexPaginator(searchResult, pokedexMenu).run();
                    }
                }).error(new ErrorHandler() {
            @Override
            public void handle(Exception e) {
                if (e instanceof NumberFormatException) {
                    System.out.println("Invalid Number. Press enter to go back.");
                }else {
                    e.printStackTrace();
                }

            }
        });

        this.sortMenu = new MenuBuilder();
        this.sortMenu.option(new MenuOption("Sort Pokemon by Name") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon();
                Pokedex.sortByName(pokemon);
                new PokedexPaginator(pokemon, pokedexMenu).run();

            }
        }).option(new MenuOption("Sort Pokemon by Weight") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon();
                Pokedex.sortByWeight(pokemon);
                new PokedexPaginator(pokemon, pokedexMenu).run();

            }
        }).option(new MenuOption("Sort Pokemon by Number") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon();
                Pokedex.sortByNumber(pokemon);
                new PokedexPaginator(pokemon, pokedexMenu).run();

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
                        sortMenu.run();
                    }
                })
                .option(new MenuOption("Open Pokedex") {
                    @Override
                    public void run() {
                        new PokedexPaginator(pokedex.getAllPokemon(), pokedexMenu).run();
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
