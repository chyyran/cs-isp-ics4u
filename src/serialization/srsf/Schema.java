package serialization.srsf;

import java.util.HashMap;
import java.util.HashSet;

public class Schema
{
    private final HashMap<String, String> schemaProperties;
    private final String schemaName;
    private final String outputType;

    public Schema(HashMap<String, String> schemaProperties, String schemaName, String outputType) {
        this.schemaProperties = schemaProperties;
        this.schemaName = schemaName;
        this.outputType = outputType;
    }

    public HashMap<String, String> getSchemaProperties() {
        return this.schemaProperties;
    }

    public String getSchemaName() {
        return this.schemaName;
    }

    public String getOutputType() {
        return this.outputType;
    }
}
