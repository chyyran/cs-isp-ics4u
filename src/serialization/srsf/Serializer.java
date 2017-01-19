package serialization.srsf;

import java.util.HashMap;
import java.util.List;

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
        sb.append(join(",", s)); //todo this is a 1.8 api.
        sb.append("]");
        return sb.toString();
    }

    private static String join(String conjunction, String[] list)
    {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (String item : list)
        {
            if (first) {
                first = false;
            } else {
                sb.append(conjunction);
            }
            sb.append(item);
        }
        return sb.toString();
    }
}
