package serialization.srsf;

import java.util.HashMap;
import java.util.Map;

public class SchemaSerializer extends Serializer<Schema>
{
    public SchemaSerializer(SerializationContext context) {
        super(context);
    }

    @Override
    public Schema deserialize(HashMap<String, KeyValuePair> keyValuePairs)
    {
        String schemaName = keyValuePairs.get("$schemaName").asString();
        String outputType = keyValuePairs.get("$outputType").asString();
        HashMap<String, String> properties = new HashMap<>();
        for(Map.Entry<String, KeyValuePair> s : keyValuePairs.entrySet()) {
            if(s.getKey().startsWith("@")) {
                properties.put(s.getKey().substring(1), s.getValue().asString());
            }
        }
        return new Schema(properties, schemaName, outputType);
    }

    @Override
    public HashMap<String, String> serialize(Schema s) {
        throw new UnsupportedOperationException("Schemas can not be changed");
    }
}
