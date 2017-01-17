package serialization.srsf;

import java.io.IOException;

/**
 * Created by Ronny on 2017-01-12.
 */
public class Test
{
    public static void main(String[] args) {
        SerializationContext sc = new SerializationContext("c:\\srsf");
        sc.addSerializer(new PokemonTypeSerializer(sc), PokemonType.class);
        sc.addSerializer(new SchemaSerializer(sc), Schema.class);
        try {
            sc.loadCollection("schema", Schema.class);
            sc.loadCollection("PokemonType", PokemonType.class);
        }catch (IOException e) {
            e.printStackTrace();
        }
        for(Schema s : sc.getCollection(Schema.class)) {
            System.out.println(s);
        }

        for(PokemonType t : sc.getCollection(PokemonType.class)) {
            System.out.println(t.getWeaknesses().get(0).getName());
        }
    }
}
