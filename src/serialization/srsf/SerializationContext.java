package serialization.srsf;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.security.Key;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

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

    @SuppressWarnings("unchecked")
    public <E> void saveCollection(Class<E> collectionClass) throws IOException {
        String schemaName = collectionClass.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append("~~!srsf~~");
        sb.append(System.getProperty("line.separator"));
        sb.append("!!");
        sb.append(schemaName);
        sb.append(System.getProperty("line.separator"));
        sb.append("---"); //build header
        List<E> collection = this.getCollection(collectionClass);
        for (E record : collection) {
            HashMap<String, String> values = this.serializers.get(collectionClass.getName()).serialize(record);
            for (HashMap.Entry<String, String> entry : values.entrySet()) {
                sb.append(entry.getKey());
                sb.append("|");
                sb.append(entry.getValue());
                sb.append(System.getProperty("line.separator"));
            }
            sb.append("---");
            sb.append(System.getProperty("line.separator"));
        }
        Files.write(Paths.get(this.directory, schemaName + ".srsf"),
                sb.toString().getBytes(), StandardOpenOption.CREATE_NEW);

    }
    public <E> void loadCollection(Class<E> collectionClass) throws IOException
    {
        String schemaName = collectionClass.getSimpleName();
        if(this.serializers.containsKey(collectionClass.getName())) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(this.directory, schemaName + ".srsf"), Charset.defaultCharset());
                HashMap<String, KeyValuePair> currentSet = new HashMap<>();
                this.collected.put(collectionClass.getName(), new ArrayList<>());
                for (String s : lines) {
                    if (s.startsWith("~~!srsf~~")) continue;
                    if (s.startsWith("!!")) continue;
                    if (s.startsWith("#")) continue;
                    if (s.isEmpty()) continue;
                    if (s.startsWith("---")) {
                        if (!currentSet.isEmpty()) {
                            E object = (E) serializers.get(collectionClass.getName()).deserialize(currentSet);
                            this.collected.get(collectionClass.getName()).add(object);
                        }
                        currentSet = new HashMap<>();
                        continue;
                    }
                    if (!s.contains("|")) continue; //be permissive
                    String[] kvp = s.split(Pattern.quote("|"), 2);
                    currentSet.put(kvp[0], new KeyValuePair(kvp[0], kvp[1]));
                }
            } catch (NoSuchFileException e) {
                StringBuilder sb = new StringBuilder();
                sb.append("~~!srsf~~");
                sb.append(System.getProperty("line.separator"));
                sb.append("!!");
                sb.append(schemaName);
                sb.append(System.getProperty("line.separator"));
                sb.append("---");
                Files.write(Paths.get(this.directory, schemaName + ".srsf"),
                        sb.toString().getBytes(), StandardOpenOption.CREATE_NEW);
                return;
            }
        }
    }
}
