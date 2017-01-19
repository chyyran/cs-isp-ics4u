package serialization.srsf;

import java.util.HashMap;

public abstract class Serializer<T>
{
    private final SerializationContext context;
    public Serializer(SerializationContext context) {
        this.context = context;
    }
    protected SerializationContext getContext() {
        return this.context;
    }
    public abstract T deserialize(HashMap<String, KeyValuePair> keyValuePairs);
    public abstract HashMap<String, String> serialize(T object);

    protected static String arrayFormat(String[] s) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(String.join(",", s));
        sb.append("]");
        return sb.toString();
    }
}
