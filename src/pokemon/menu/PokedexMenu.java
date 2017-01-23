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

/**
 * The Pokedex menu allows users to search for Pokedex
 */
public class PokedexMenu extends MenuOption {

    /**
     * The root submenu
     */
    private final MenuBuilder pokedexMenu;
    /**
     * The search submenu
     */
    private final MenuBuilder searchMenu;
    /**
     * The sort submenu
     */
    private final MenuBuilder sortMenu;

    /**
     * The instance of Pokedex fr this menu
     */
    private final Pokedex pokedex;

    /**
     * The list of types of Pokemon
     */
    private final List<PokemonType> types;

    /**
     * Instantiate the PokedexMenu
     * @param pokedex The pokedex to search and sort through
     * @param types The list of types of Pokemon
     */
    protected PokedexMenu(final Pokedex pokedex, final List<PokemonType> types) {
        super("Pokedex");
        this.pokedex = pokedex;
        this.types = types;
        final Scanner sc = new Scanner(System.in);

        //Instantiate and build the search menu.
        this.searchMenu = new MenuBuilder();
        this.searchMenu
                .option(new MenuOption("Search by Name") {
                    @Override
                    public void run() {
                        //search pokemon by name
                        System.out.println("Please enter a name: ");
                        String searchQuery = sc.nextLine();
                        PokemonSpecies searchResult = pokedex.searchPokemonByName(searchQuery); //search the pokedex
                        if(searchResult != null ) {
                            System.out.println(searchResult);
                        }else{
                            System.out.println("That Pokemon was not found. Please check your spelling!");
                        }
                    }
                })
                .option(new MenuOption("Search by Number") {
                    @Override
                    public void run() {
                        //search pokemon by number
                        System.out.println("Please enter a Number: ");
                        int searchQuery = Integer.parseInt(sc.nextLine());
                        PokemonSpecies searchResult = pokedex.getPokemon(searchQuery); //get the pokemon from the pokedex
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
                        //search pokemon by type
                        System.out.println("Available Pokemon Types: ");
                        int typeSelect = 0;

                        //display the list of available types
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
                        List<PokemonSpecies> searchResult = pokedex.searchPokemonByType(type); //search the pokedex
                        new PokedexPaginator(searchResult).run(); //paginate the results
                    }
                }).error(new ErrorHandler() {
                    @Override
                    public void handle(Exception e) {
                        if (e instanceof NumberFormatException) {
                            System.out.println("Invalid choice. Press enter to go back."); //handle invalid coices
                        }else {
                            e.printStackTrace();
                        }

                    }
        });

        //build and instantiate the sorting menu
        this.sortMenu = new MenuBuilder();
        this.sortMenu.option(new MenuOption("Sort Pokemon by Name") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon(); //get a copy of the list of pokemon
                Pokedex.sortByName(pokemon); //sort the pokemon by name
                new PokedexPaginator(pokemon).run(); //paginate the results

            }
        }).option(new MenuOption("Sort Pokemon by Weight") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon(); //get a copy of the list of pokemon
                Pokedex.sortByWeight(pokemon); //sort the pokemon by weight
                new PokedexPaginator(pokemon).run(); //paginate the results

            }
        }).option(new MenuOption("Sort Pokemon by Number") {
            @Override
            public void run() {
                List<PokemonSpecies> pokemon = pokedex.getAllPokemon(); //get a copy of the list of pokemon
                Pokedex.sortByNumber(pokemon); //sort the pokemon by number
                new PokedexPaginator(pokemon).run(); //paginate the results

            }
        });

        //build and instantiate the root menu
        this.pokedexMenu = new MenuBuilder();
        this.pokedexMenu
                .option(new MenuOption("Search for Pokemon") {
                    @Override
                    public void run() {
                        //run the search menu
                        searchMenu.run();
                    }
                })
                .option(new MenuOption("Sort Pokemon") {
                    @Override
                    public void run() {
                        //run the sort meun
                        sortMenu.run();
                    }
                })
                .option(new MenuOption("Open Pokedex") {
                    @Override
                    public void run() {
                        //displays a paginated list of all the pokemon
                        new PokedexPaginator(pokedex.getAllPokemon()).run();
                    }
                });
    }

    @Override
    public void run() {
        this.pokedexMenu.run();
    }

    /**
     * Paginates a list of Pokemon
     */
    private class PokedexPaginator extends MenuOption {
        /**
         * How many Pokemon to show per page
         */
        public static final int POKEMON_PER_PAGE = 5;
        /**
         * The list to paginate
         */
        private final List<PokemonSpecies> pokemon;
        /**
         * The root menu to get to the paginated results
         */
        private final MenuBuilder menuBuilder;

        /**
         * Instantiate the paginator
         * @param pokemon The list of Pokemon to paginate
         */
        public PokedexPaginator(List<PokemonSpecies> pokemon) {
            super("Pokedex Paginator");
            this.pokemon = pokemon;
            this.menuBuilder = new MenuBuilder();
            menuBuilder.option(new MenuOption("View Results..") {
                @Override
                public void run() {
                    buildMenu(0, POKEMON_PER_PAGE).run();
                }
            });
        }

        /**
         * Recursively builds the paginated menu
         * @param pokemonStart The index to start with from the last page
         * @param length How many Pokemon to display
         * @return A MenuBuilder with options for the given section of Pokemon
         */
        private MenuBuilder buildMenu(final int pokemonStart,
                                      final int length) {
            final MenuBuilder nextFivePokemon = new MenuBuilder();
            for (int i = 0; i < pokemon.size() && i < length; i++) {
                final PokemonSpecies pokemonSpecies = pokemon.get(i + pokemonStart);
                nextFivePokemon.option(new MenuOption(pokemonSpecies.getName()) {
                    //Each Pokemon has an option to display further details
                    @Override
                    public void run() {
                        System.out.println(pokemonSpecies);
                    }
                });
            }
            if (!(pokemonStart + length + 1 > pokemon.size())) {
                nextFivePokemon.option(new MenuOption("Next page") {
                    //Going to the next page will build a new menu with the new offsets
                    @Override
                    public void run() {
                        buildMenu(pokemonStart + length, length).run();
                    }
                });
            }
            return nextFivePokemon;
        }

        /**
         * Runs the pokedex menu
         */
        @Override
        public void run() {
            this.menuBuilder.run();
        }
    }
}
