//Pokedex Class
package pokemon.core;

import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonType;

import java.util.ArrayList;
import java.util.List;

public class Pokedex {
   private List<PokemonSpecies> pokemonSpecies;
   private static int NOTHERE = -1;
   public Pokedex (List<PokemonSpecies> list) {
      pokemonSpecies=list;
   } 
   
   public List<PokemonSpecies> searchPokemonByType (PokemonType type) {
      List<PokemonSpecies> list= new ArrayList<PokemonSpecies> ();
      for (PokemonSpecies all : pokemonSpecies) {
         if (all.getPrimaryType()== type || all.getSecondaryType()== type)
            list.add(all);
      }
      return list;
   }
   
   public PokemonSpecies searchPokemonByName (String name) {
      List<PokemonSpecies> list = new ArrayList<PokemonSpecies>(pokemonSpecies);
      //Pokedex.sortByName(list);
      return Pokedex.searchName(name, list);
   }
   
   public static PokemonSpecies searchName (String name, List<PokemonSpecies> list) {
      int middle = list.size()/2;
      if (list.get(middle).getName().equals(name))
         return list.get(middle);
      return null;
   }
}