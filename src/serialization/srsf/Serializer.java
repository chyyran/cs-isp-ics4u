package serialization.srsf;

import java.util.HashMap;
import java.util.List;

/**
 * A serializer to convert the key value pairs of a single SRSF block into an object of the given class
 *
 * @param <T> The class this serializer will convert to
 */
public abstract class Serializer<T> {
    /**
     * The context this serializer has been loaded into
     */
    private final SerializationContext context;

    /**
     * Instantiates a serializer into a given serialization context
     *
     * @param context
     */
    public Serializer(SerializationContext context) {
        this.context = context;
    }

    /**
     * Gets the serialization context for this serializer
     *
     * @return
     */
    protected SerializationContext getContext() {
        return this.context;
    }

    /**
     * Converts the key value pairs of a single SRSF block into an object of the given class
     *
     * @param keyValuePairs The key value pairs of the SRSF block
     * @return The deserialized object
     */
    public abstract T deserialize(HashMap<String, KeyValuePair> keyValuePairs);

    /**
     * Converts an object of the given class to key value pairs, with the string representation of it's value,
     * such that they can be formatted into an SRSF block and reconstructed using the deserialize(HashMap) method
     *
     * @param object The object to deserialize
     * @return A hashmap of string key value pairs read to be written to disk.
     */
    public abstract HashMap<String, String> serialize(T object);

    /**
     * Helper method to format a string array in SRSF array literal format
     * eg. [item1,item2,...]
     *
     * @param stringArray The array to format
     * @return The array literal representing the contents of the string arry.
     */
    protected static String arrayFormat(String[] stringArray) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(join(",", stringArray));
        sb.append("]");
        return sb.toString();
    }

    /**
     * Helper method that joins an array of strings into a single string with the given separator
     *
     * @param separator The string to separate each entry with
     * @param list The string array to join
     * @return The joined string.
     */
    private static String join(String separator, String[] list) {
        StringBuilder sb = new StringBuilder();
        boolean initial = true;
        for (String item : list) {
            if (initial) {
                initial = false;
            } else {

                sb.append(separator);
            }
            sb.append(item);
        }
        return sb.toString();
    }
}
