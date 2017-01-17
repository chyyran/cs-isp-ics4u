import com.sun.prism.impl.Disposer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

/**
 * Created by ronny on 2016-12-29.
 */
public class Record implements Iterable<RecordValue> {

    private HashMap<String, RecordValue> values;
    private final String subKey;

    protected Record(){
        this("$", new HashMap<>());
    };

    private Record(String subKey, Record record) {
        this(subKey, record.values);
    }

    protected Record(String subKey, HashMap<String, RecordValue> values) {
        this.subKey = subKey;
        this.values = values;
    }


    protected void setInt(final String key, int value) {
        this.values.put(this.subKey + key, new RecordValue(key, value, RecordPrimitive.INTEGER));
    }

    protected int getInt(final String key) {
        if(!this.values.containsKey(this.subKey + key)) return 0;
        return (int)this.values.get(this.subKey + key).getValue();
    }

    protected void setBoolean(final String key, int value) {
        this.values.put(this.subKey + key, new RecordValue(key, value, RecordPrimitive.BOOLEAN));
    }

    protected boolean getBoolean(final String key) {
        if(!this.values.containsKey(this.subKey + key)) return false;
        return (boolean)this.values.get(this.subKey + key).getValue();
    }

    protected void setDouble(final String key, int value) {
        this.values.put(this.subKey + key, new RecordValue(key, value, RecordPrimitive.DOUBLE));
    }

    protected double getDouble(final String key) {
        if(!this.values.containsKey(this.subKey + key)) return 0;
        return (double)this.values.get(this.subKey + key).getValue();
    }
    
    protected void setString(final String key, String value) {
        this.values.put(this.subKey + key, new RecordValue(key, value, RecordPrimitive.STRING));
    }

    protected String getString(final String key) {
        if(!this.values.containsKey(this.subKey + key)) return null;
        return (String)this.values.get(this.subKey + key).getValue();
    }

    @Override
    public final Iterator<RecordValue> iterator() {
        return this.values.values().iterator();
    }
}
