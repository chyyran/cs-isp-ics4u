//Pokemon Species Class

  public class PokemonSpecies {
    private int number;
    private String name;
    private PokemonType primaryType;
    private PokemonType secondaryType;
    private double weight;
    private PokemonSpecies evolution;
    private PokemonSpecies preEvolution;
    
    public String getName(){
      return name;
    }
    
    public PokemonType getPrimaryType(){
      return primaryType;
    }
    
    public PokemonType getSecondaryType(){
      return secondaryType;
    }
    
    public double getWeight() {
      return weight;
    }
    
    public int getNumber() {
       return number;
    }
    
    public PokemonSpecies getNextEvolution() {
      return evolution;
    }
    
    public PokemonSpecies get PreviousEvolution() {
      return preEvolution;
    }
    
    public boolean equals(PokemonSpecies that) {
      return (that.getNumber()==getNumber());
    }
  }