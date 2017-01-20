//Pokedex Class
package pokemon.core;

import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonType;
import pokemon.data.comparators.*;

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
      Pokedex.sortByName(list);
      return Pokedex.searchName(name, list);
   }
   
   public static PokemonSpecies searchName (String name, List<PokemonSpecies> list) {
      int middle = list.size()/2;
      
      if (list.get(middle).getName().equals(name)) {
         return list.get(middle);         
      }
      else if (list.size() == 1) {
         return null;
      }
      else if (list.get(middle).getName().compareTo(name) > 0) {
         return searchName(name, list.subList(0, middle-1));
      }
      else {
         return searchName(name, list.subList(middle+1, list.size()-1));
      }
   }
   
   public List<PokemonSpecies> getAllPokemon () {
      return pokemonSpecies;
   }
   
   public PokemonSpecies getPokemon(int num) {
      for (int i=0; i<pokemonSpecies.size(); i++) {
         if (pokemonSpecies.get(i).getNumber()==num)
            return pokemonSpecies.get(i);
      }
      return null;      
   }
   
   public static void sortByName (List<PokemonSpecies> list) {
      PokemonSpeciesNameComparator comparator = new PokemonSpeciesNameComparator();
      boolean sorted=false;
      PokemonSpecies temp;
      
      for (int i=list.size()-1; i>0&&sorted==false; i--) {
         sorted=true;
         for (int j=0; j<i; j++) {
            if (comparator.compare(list.get(j), list.get(j+1))<0) {
               sorted=false;
               temp=list.get(j);
               list.add(j, list.get(j+1));
               list.add(j+1, temp);
            }
         }
      }  
   }
   
   public static void sortByNumber (List<PokemonSpecies> list) {
      PokemonSpeciesNumberComparator comparator = new PokemonSpeciesNumberComparator();
      boolean sorted=false;
      PokemonSpecies temp;
      
      for (int i=list.size()-1; i>0&&sorted==false; i--) {
         sorted=true;
         for (int j=0; j<i; j++) {
            if (comparator.compare(list.get(j), list.get(j+1))<0) {
               sorted=false;
               temp=list.get(j);
               list.add(j, list.get(j+1));
               list.add(j+1, temp);
            }
         }
      }  
   }
   
   public static void sortByWeight (List<PokemonSpecies> list) {
      PokemonSpeciesWeightComparator comparator = new PokemonSpeciesWeightComparator();
      boolean sorted=false;
      PokemonSpecies temp;
      
      for (int i=list.size()-1; i>0&&sorted==false; i--) {
         sorted=true;
         for (int j=0; j<i; j++) {
            if (comparator.compare(list.get(j), list.get(j+1))<0) {
               sorted=false;
               temp=list.get(j);
               list.add(j, list.get(j+1));
               list.add(j+1, temp);
            }
         }
      }  
   }
}                            
