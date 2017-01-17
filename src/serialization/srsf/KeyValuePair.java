package serialization.srsf;

/**
 * Created by Ronny on 2017-01-12.
 */
public class KeyValuePair
{
    private final String value;
    private final String key;
    public KeyValuePair(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
    public String[] asStringArray() {
        return value.split(",");
    }

    public String asString() {
        return value;
    }

    public int[] asIntArray() {
        String[] strValues = value.split(",");
        int[] retVal = new int[strValues.length];
        for(int i = 0; i < strValues.length; i++) {
            retVal[i] = Integer.parseInt(strValues[i]);
        }
        return retVal;
    }


    public double[] asDoubleArray() {
        String[] strValues = value.split(",");
        double[] retVal = new double[strValues.length];
        for(int i = 0; i < strValues.length; i++) {
            retVal[i] = Double.parseDouble(strValues[i]);
        }
        return retVal;
    }

    public boolean[] asBooleanArray() {
        String[] strValues = value.split(",");
        boolean[] retVal = new boolean[strValues.length];
        for(int i = 0; i < strValues.length; i++) {
            retVal[i] = Boolean.parseBoolean(strValues[i]);
        }
        return retVal;
    }

    public boolean asBoolean() {
        return Boolean.parseBoolean(this.value);
    }

    public int asInt() {
        return Integer.parseInt(this.value);
    }

    public double asDouble() {
        return Double.parseDouble(this.value);
    }

}
