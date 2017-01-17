package serialization.srsf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Ronny on 2017-01-12.
 */
public class SerializationContext
{
    private final String directory;
    private final HashMap<String, Serializer> serializers;
    private final HashMap<String, List> collected;
    public SerializationContext(String directory) {
        this.directory = directory;
        this.serializers = new HashMap<String, Serializer>();
        this.collected = new HashMap<String, List>();
    }


    public <E> void addSerializer(Serializer<E> serializer, Class<E> serializerClass) {
        this.serializers.put(serializerClass.getName(), serializer);
    }

    @SuppressWarnings("unchecked")
    public <E> List<E> getCollection(Class<E> collectionClass) {
        return this.collected.get(collectionClass.getName());
    }

    public <E> void loadCollection(String schemaName, Class<E> collectionClass) throws IOException
    {
        if(this.serializers.containsKey(collectionClass.getName())) {
            List<String> lines = Files.readAllLines(Paths.get(this.directory, schemaName+".srsf"), Charset.defaultCharset());
            HashMap<String, KeyValuePair> currentSet = new HashMap<>();
            this.collected.put(collectionClass.getName(), new ArrayList<>());
            for(String s : lines){
                if(s.startsWith("~~!srsf~~")) continue;
                if(s.startsWith("!!")) continue;
                if(s.startsWith("#")) continue;
                if(s.isEmpty()) continue;
                if(s.startsWith("---")) {
                    if(!currentSet.isEmpty()) {
                        E object = (E)serializers.get(collectionClass.getName()).toObject(currentSet);
                        this.collected.get(collectionClass.getName()).add(object);
                    }
                    currentSet = new HashMap<>();
                    continue;
                }
                if(!s.contains("|")) continue; //be permissive
                String[] kvp = s.split(Pattern.quote("|"), 2);
                currentSet.put(kvp[0], new KeyValuePair(kvp[0], kvp[1]));
            }
        }
    }
}
