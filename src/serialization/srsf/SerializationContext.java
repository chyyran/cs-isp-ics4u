package serialization.srsf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Pattern;

/**
 * The context in which the collection of objects is loaded.
 * Lazy<T> should be used in conjunction with LazyResolver<T>
 * to resolve object references within the context, thus,
 * object references in serialized SRSF text format must be self-contained
 * within the SerializationContext.
 *
 * Serializes must be added for a given class before being able to be loaded into the context.
 *
 * @see Lazy
 * @see LazyResolver
 */
public class SerializationContext
{
    public static final String SRSF_BLOCK_DELIMETER = "---";
    public static final String SRSF_FILE_HEADER = "~~!srsf~~";
    public static final String SRSF_TYPE_MARKER = "!!";
    public static final String SRSF_COMMENT_MARKER = "#";
    public static final String SRSF_FILE_EXT = ".srsf";
    public static final String SRSF_NULL_MARKER = "@@NUL@@";
    public static final String SRSF_VALUE_SEPARATOR = "|";
    /**
     * The root directory of the serialization context
     */
    private final String directory;
    /**
     * A collection of registered serializers
     */
    private final HashMap<String, Serializer> serializers;
    /**
     * The collection of loaded items
     */
    private final HashMap<String, List> collected;

    /**
     * Initializes the serialization context for the given directory
     * @param directory The directory of the context, must contain .srsf files.
     */
    public SerializationContext(String directory) {
        this.directory = directory;
        this.serializers = new HashMap<String, Serializer>();
        this.collected = new HashMap<String, List>();
    }

    /**
     * Registers a serializer to context
     * @param serializer The serializer that converts this class from SRSF blocks
     * @param serializerClass The class definition of the class this converter is for
     * @param <E> The class this converter is for
     */
    public <E> void addSerializer(Serializer<E> serializer, Class<E> serializerClass) {
        this.serializers.put(serializerClass.getName(), serializer);
    }

    /**
     * Gets the loaded collection of objects for the given class
     * @param collectionClass The class definition of the given class
     * @param <E> The class this converter is for
     * @return A list of loaded objects of the given class.
     */
    @SuppressWarnings("unchecked")
    public <E> List<E> getCollection(Class<E> collectionClass) {
        return this.collected.get(collectionClass.getName());
    }

    /**
     * Saves the contents of the currently loaded collection into the SRSF file
     * under the context root.
     * @param collectionClass The class definition of the given class
     * @param <E> The class this converter is for
     * @throws IOException If the file is unable to be written.
     */
    @SuppressWarnings("unchecked")
    public <E> void saveCollection(Class<E> collectionClass) throws IOException {
        final String newline = System.getProperty("line.separator"); //gets the newline for this environment
            //crlf for windows, lf for posix-based systems
        String schemaName = collectionClass.getSimpleName();
        StringBuilder sb = new StringBuilder();
        sb.append(SRSF_FILE_HEADER);
        sb.append(newline);
        sb.append(SRSF_TYPE_MARKER);
        sb.append(schemaName);
        sb.append(newline);
        sb.append(SRSF_BLOCK_DELIMETER);
        sb.append(newline); //write the header
        List<E> collection = this.getCollection(collectionClass); //gets the loaded objects
        for (E record : collection) {
            if(record == null) continue; //skip null records
            HashMap<String, String> values = this.serializers.get(collectionClass.getName()).serialize(record);
                //use the serializer for this class to get string values
            for (Map.Entry<String, String> entry : values.entrySet()) { //write each value to a block
                sb.append(entry.getKey());
                sb.append(SRSF_VALUE_SEPARATOR);
                sb.append(entry.getValue() != null ? entry.getValue() : SRSF_NULL_MARKER);
                sb.append(newline);
            }
            sb.append(SRSF_BLOCK_DELIMETER); //close a single SRSF block
            sb.append(newline);
        }

        //flush the data to file.
        Files.write(Paths.get(this.directory, schemaName + SRSF_FILE_EXT),
                sb.toString().getBytes(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

    }

    /**
     * Loads a collection from the context root, with the class name being the same as the SRSF file name.
     * This method can only be used once per context per class type.
     * @param collectionClass The class definition of the given class
     * @param <E> The class this converter is for
     * @throws IOException If the file is unable to be read.
     */
    public <E> void loadCollection(Class<E> collectionClass) throws IOException
    {
        String schemaName = collectionClass.getSimpleName(); //gets the name of the class
        this.collected.put(collectionClass.getName(), new ArrayList<>()); //create a list for the loaded collections
        if(this.serializers.containsKey(collectionClass.getName())) {
            try {
                List<String> lines = Files.readAllLines(Paths.get(this.directory, schemaName + SRSF_FILE_EXT), Charset.defaultCharset());
                    //read in the data
                HashMap<String, KeyValuePair> currentBlock = new HashMap<>(); //the hashmap to store the current block data
                for (String s : lines) {
                    if (s.startsWith(SRSF_FILE_HEADER)) continue;
                    if (s.startsWith(SRSF_TYPE_MARKER)) continue;
                    if (s.startsWith(SRSF_COMMENT_MARKER)) continue;
                    if (s.isEmpty()) continue; //skip headers and comments
                        //todo: verify the header
                    if (s.startsWith(SRSF_BLOCK_DELIMETER)) {
                        if (!currentBlock.isEmpty()) { //if the current line is a block end marker, deserialize the values and push it to the stack.
                            E object = (E) serializers.get(collectionClass.getName()).deserialize(currentBlock);
                            this.collected.get(collectionClass.getName()).add(object);
                        }
                        //create a new hashmap for the set
                        currentBlock = new HashMap<>();
                        continue;
                    }
                    if (!s.contains(SRSF_VALUE_SEPARATOR)) continue; //if a line does not contain the separator, skip it
                    String[] kvp = s.split(Pattern.quote(SRSF_VALUE_SEPARATOR), 2); //we need to use pattern.quote to escape java's regex parsing.
                    currentBlock.put(kvp[0], new KeyValuePair(kvp[0], kvp[1])); //put the current line as a key value pair in the currently-parsing block
                }
            } catch (NoSuchFileException e) {
                //if no file is found, create it.
                final String newline = System.getProperty("line.separator"); //gets the newline for this environment
                //crlf for windows, lf for posix-based systems
                StringBuilder sb = new StringBuilder();
                sb.append(SRSF_FILE_HEADER);
                sb.append(newline);
                sb.append(SRSF_TYPE_MARKER);
                sb.append(schemaName);
                sb.append(newline);
                sb.append(SRSF_BLOCK_DELIMETER);
                Files.write(Paths.get(this.directory, schemaName + SRSF_FILE_EXT),
                        sb.toString().getBytes(), StandardOpenOption.CREATE_NEW);
                return;
            }
        }
    }
}
