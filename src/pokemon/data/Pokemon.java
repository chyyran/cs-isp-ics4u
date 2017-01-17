// Pokemon Class

   public class Pokemon {
      private Lazy<PokemonSpecies> species;
      private List<Lazy<PokemonMove>> moves;
      private String name;
      private int level;
      private String id;
      private int maxHp;
      private int currentHp;
      private static int MULTIPLIER = 4;
      
      public Pokemon (String id, Lazy<PokemonSpecies> species, List<Lazy<PokemonMove>> moves, String name, int level, String id) {
         this.id=id;
         this.species=species;
         this.moves=moves;
         this.name=name;
         this.level=level;
         this.id=id;        
         maxHp=level*MULTIPLIER;
         currentHp=macHp;
      }
      
      public PokemonSpecies getSpecies(){
         return species.getValue();
      }
      public List<PokemonMove> getMoves() {
         List<PokemonMove> temp = new List<PokemonMove> ();
         for (Lazy<PokemonMove> m: PokemonMove) {
            temp.add(m.getValue());
         }
         return temp;
      }
      public String getNickname () {
         return name;
      }
      public int getLevel () {
         return level;
      }
      public void setLevel (int num) {
         level=num;
         maxHp=level*MULTIPLIER;
      }
      public int getMaxHp() {
         return maxHp;
      }
      public int getCurrentHp() {
         return currentHp;
      }
      public void setHp (int num) {
         if (num<maxHp) 
            currentHp=num;
         else
            currentHp=maxHp;   
      }     
      public boolean isFainted() {
         return currentHp==0;
      }   
   }