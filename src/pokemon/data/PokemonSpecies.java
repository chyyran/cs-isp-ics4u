//Pokemon Species Class

  public class PokemonSpecies {
    private int number;
    private String name;
    private Lazy<PokemonType> primaryType;
    private Lazy<PokemonType> secondaryType;
    private double weight;
    private Lazy<PokemonSpecies> evolution;
    private Lazy<PokemonSpecies> preEvolution;
    
    public PokemonSpecies (String name, int num, Lazy<PokemonType> type1, Lazy<PokemonType> type2, double weight, Lazy<PokemonSpecies> evolvesTo, Lazy<PokemonSpecies> evolvesFrom) {
      numver=num;
      this.name=name;
      primaryType=type1;
      secondaryType=type2;
      this.weight=weight;
      evolution=evolvesTo;
      preEvolution=evolvesFrom;
    }
    
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
