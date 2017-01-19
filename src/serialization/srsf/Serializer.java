package serialization.srsf;

import java.util.HashMap;

/**
 * Created by Ronny on 2017-01-12.
 */
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
}
