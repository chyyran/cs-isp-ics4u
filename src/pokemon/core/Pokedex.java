//Pokedex Class
package pokemon.core;

import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonType;
import pokemon.data.comparators.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Pokedex manages the list of available PokemonSpecies, and provides
 * sorting and searching algorithms to manage and display lists of PokemonSpecies
 */
public class Pokedex {

    /**
     * A list of all Pokemon species registered for this Pokedex
     */
    private List<PokemonSpecies> pokemonSpecies;

    /**
     * Instantiate the Pokedex with the given list of species
     * @param list The list of all Pokemon species registered
     */
    public Pokedex(List<PokemonSpecies> list) {
        pokemonSpecies = list;
    }

    /**
     * Returns a list of all Pokemon species with the given type.
     * An implementation of Sequential Search
     * @param type The type to searchw ith
     * @return A list of all Pokemon species with the given type
     */
    public List<PokemonSpecies> searchPokemonByType(PokemonType type) {
        List<PokemonSpecies> list = new ArrayList<>();
        for (PokemonSpecies all : pokemonSpecies) {
            if (all.getPrimaryType() == type || all.getSecondaryType() == type)
                list.add(all);
        }
        return list;
    }

    /**
     * Returns the Pokemon with the given name, ignoring case
     * @param name The name of the Pokemon
     * @return The Pokemon with the given name.
     */
    public PokemonSpecies searchPokemonByName(String name) {
        List<PokemonSpecies> list = new ArrayList<>(pokemonSpecies);
        Pokedex.sortByName(list);
        return Pokedex.searchName(name, list);
    }

    /**
     * A recursive binary search for searching Pokemon by Name
     * @param name The name of the PokemonSpecies, ignoring case
     * @param list The list of PokemonSpecies to search in.
     * @return The PokemonSpecies with the given name, or null if it does not exist.
     */
    private static PokemonSpecies searchName(String name, List<PokemonSpecies> list) {
        if (list.size() == 0) return null;
        int middle = list.size() / 2;
        if (list.get(middle).getName().equalsIgnoreCase(name)) {
            return list.get(middle);
        } else if (list.size() == 1) {
            return null;
        } else if (list.get(middle).getName().toLowerCase().compareTo(name.toLowerCase()) < 0) {
            return searchName(name, list.subList(middle + 1, list.size()));
        } else {
            return searchName(name, list.subList(0, middle));
        }
    }

    /**
     * Returns an immutable copy of the list of all registered Pokemon
     * @return A list of all Pokemon in the Pokedex
     */
    public List<PokemonSpecies> getAllPokemon() {
        return new ArrayList<>(pokemonSpecies);
    }

    /**
     * Gets a Pokemon with the given number. Implemented using sequential search
     * @param num The number of the Pokemon
     * @return The Pokemon with the given number.
     */
    public PokemonSpecies getPokemon(int num) {
        for (int i = 0; i < pokemonSpecies.size(); i++) {
            if (pokemonSpecies.get(i).getNumber() == num)
                return pokemonSpecies.get(i);
        }
        return null;
    }

    /**
     * Sorts the list of Pokemon with the given comparator in ascending order
     * @param list The list of Pokemon to sort
     * @param comparator The PokemonSpecies comparator to use in the sort.
     */
    private static void sort(List<PokemonSpecies> list, Comparator<PokemonSpecies> comparator) {
        boolean sorted = false;
        PokemonSpecies temp;
        for (int i = list.size() - 1; i > 0 && !sorted; i--) {
            sorted = true;
            for (int j = 0; j < i; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > 0) {
                    sorted = false;
                    temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    /**
     * Sorts a list of PokemonSpecies by their name in alphabetical order
     * @param list The list of Pokemon to sort
     */
    public static void sortByName(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesNameComparator());
    }

    /**
     * Sorts a list of PokemonSpecies by their Pokemon number in ascending order
     * @param list The list of Pokemon to sort
     */
    public static void sortByNumber(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesNumberComparator());
    }

    /**
     * Sorts a list of PokemonSpecies by their weight in ascending order
     * @param list The list of Pokemon to sort.
     */
    public static void sortByWeight(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesWeightComparator());

    }
}                            
