//PokemonMove class

   public class PokemonMove {
      private String name;
      private Lazy<PokemonType> type;
      private double baseDamage;
      private double selfDamage;
      
      public PokemonMove (String name, Lazy<PokemonType> type, double damage, double selfDamage) {
         this.name=name;
         this.type=type;
         baseDamage=damage;
         this.selfDamage=selfDamage;
      }
      
      public String getName(){
         return name;
      }
      
      public int getDamage() {
         return baseDamage;
      }
      
      public int getSelfDamage(){
         return selfDamage;
      }
      
      public PokemonType getType(){
         return type;
      }
      
      public boolean equals (PokemonMove that){
         return (that.getName().equals(getName()));
      }
   }