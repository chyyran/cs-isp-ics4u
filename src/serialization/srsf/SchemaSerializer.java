package serialization.srsf;

import java.util.HashMap;
import java.util.Map;

/**
 * A builtin serializer for Schema.srsf schematics.
 * @see Schema
 */
public class SchemaSerializer extends Serializer<Schema>
{

    public static final String SCHEMA_PROPERTY_PREFIX = "@";
    public static final String $_SCHEMA_NAME = "$schemaName";
    public static final String $_OUTPUT_TYPE = "$outputType";

    /**
     * Initializes the serializer into the given context
     * @param context The context to load the serializer into
     */
    public SchemaSerializer(SerializationContext context) {
        super(context);
    }

    /**
     * Serializes the SRSF block into a schema object
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The serialzies schema
     */
    @Override
    public Schema deserialize(HashMap<String, KeyValuePair> keyValuePairs)
    {
        String schemaName = keyValuePairs.get($_SCHEMA_NAME).asString(); //get the schema name
        String outputType = keyValuePairs.get($_OUTPUT_TYPE).asString(); //get the output type
        HashMap<String, String> properties = new HashMap<>(); //get the properties
        for(Map.Entry<String, KeyValuePair> s : keyValuePairs.entrySet()) {
            if(s.getKey().startsWith(SCHEMA_PROPERTY_PREFIX)) {
                properties.put(s.getKey().substring(1), s.getValue().asString());
            }
        }
        return new Schema(properties, schemaName, outputType);
    }

    /**
     * Schemas are read only, and can not be written back to file.
     * @throws UnsupportedOperationException
     */
    @Override
    public HashMap<String, String> serialize(Schema s) {
        throw new UnsupportedOperationException("Schemas can not be changed");
    }
}
