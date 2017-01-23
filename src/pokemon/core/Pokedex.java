//Pokedex Class
package pokemon.core;

import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonType;
import pokemon.data.comparators.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Pokedex {

    private List<PokemonSpecies> pokemonSpecies;

    public Pokedex(List<PokemonSpecies> list) {
        pokemonSpecies = list;
    }

    public List<PokemonSpecies> searchPokemonByType(PokemonType type) {
        List<PokemonSpecies> list = new ArrayList<>();
        for (PokemonSpecies all : pokemonSpecies) {
            if (all.getPrimaryType() == type || all.getSecondaryType() == type)
                list.add(all);
        }
        return list;
    }

    public PokemonSpecies searchPokemonByName(String name) {
        List<PokemonSpecies> list = new ArrayList<>(pokemonSpecies);
        Pokedex.sortByName(list);
        return Pokedex.searchName(name, list);
    }

    public static PokemonSpecies searchName(String name, List<PokemonSpecies> list) {
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

    public List<PokemonSpecies> getAllPokemon() {
        return new ArrayList<>(pokemonSpecies);
    }

    public PokemonSpecies getPokemon(int num) {
        for (int i = 0; i < pokemonSpecies.size(); i++) {
            if (pokemonSpecies.get(i).getNumber() == num)
                return pokemonSpecies.get(i);
        }
        return null;
    }

    private static void sort(List<PokemonSpecies> list, Comparator comparator) {
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

    public static void sortByName(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesNameComparator());
    }

    public static void sortByNumber(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesNumberComparator());
    }

    public static void sortByWeight(List<PokemonSpecies> list) {
        Pokedex.sort(list, new PokemonSpeciesWeightComparator());

    }
}                            
