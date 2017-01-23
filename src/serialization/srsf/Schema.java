package serialization.srsf;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Represents the internal structure of an SRSF block in a computer-usable format
 */
public class Schema
{
    /**
     * The properties, or field names, of the schema.
     */
    private final HashMap<String, String> schemaProperties;
    /**
     * The name of the schema, usually the simple class name of the serializes class.
     */
    private final String schemaName;
    /**
     * The fully qualified name of the output class.
     */
    private final String outputType;

    /**
     * Initializes the schema with the given properties, name, and fully qualified name
     * @param schemaProperties The properties of the schema
     * @param schemaName The name of the schema
     * @param outputType The fully qualified name of the class this schema represents
     */
    public Schema(HashMap<String, String> schemaProperties, String schemaName, String outputType) {
        this.schemaProperties = schemaProperties;
        this.schemaName = schemaName;
        this.outputType = outputType;
    }

    /**
     * Gets the properties of the schema as key value pairs
     * @return The properties of the schema
     */
    public HashMap<String, String> getSchemaProperties() {
        return this.schemaProperties;
    }

    /**
     * Gets the name of the schema
     * @return The name of the schema
     */
    public String getSchemaName() {
        return this.schemaName;
    }

    /**
     * Gets the fully qualified name of the class this schema represents
     * @return The fully qualified name of the class this schema represents
     */
    public String getOutputType() {
        return this.outputType;
    }
}
