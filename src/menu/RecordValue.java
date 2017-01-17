/**
 * Created by ronny on 2016-12-29.
 */
public class RecordValue {

    public final static RecordValue NULL_RECORD = new RecordValue(null, null, RecordPrimitive.NULL);
    private String key;
    private Object value;
    private RecordPrimitive primitiveType;

    public RecordValue(String key, Object value, RecordPrimitive primitiveType) {
        this.key = key;
        this.value = value;
        this.primitiveType = primitiveType;
    }

    public Object getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return key + ":" + value + ":" + primitiveType.toString();
    }

    public static RecordValue parse(String recordValue) {
        return NULL_RECORD;
    }
}
