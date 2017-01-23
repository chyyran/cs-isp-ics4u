package serialization.srsf;

/**
 * A helper class that represents a single SRSF key value pair.
 */
public class KeyValuePair {
    /**
     * The string representation of the value
     */
    private final String value;
    /**
     * The key of the key value pair
     */
    private final String key;
    private final static String SRSF_NULL_STRING = "@@NUL@@";

    /**
     * Instantiates a key value pair with the given key and value
     * @param key The key of the key value pair
     * @param value The string representation of the value of the key value pair
     */
    KeyValuePair(String key, String value) {
        this.value = value;
        this.key = key;
    }

    /**
     * Gets the key of the key value pair
     * @return
     */
    public String getKey() {
        return this.key;
    }

    public String[] asStringArray() {
        if (value.equals(SRSF_NULL_STRING)) return null;
        return value.substring(1, value.length() - 1).split(",");
    }

    public String asString() {
        if (value.equals(SRSF_NULL_STRING)) return null;
        return value;
    }

    public int[] asIntArray() {
        if (value.equals(SRSF_NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        int[] retVal = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Integer.parseInt(strValues[i]);
        }
        return retVal;
    }


    public double[] asDoubleArray() {
        if (value.equals(SRSF_NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        double[] retVal = new double[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Double.parseDouble(strValues[i]);
        }
        return retVal;
    }

    public boolean[] asBooleanArray() {
        if (value.equals(SRSF_NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        boolean[] retVal = new boolean[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Boolean.parseBoolean(strValues[i]);
        }
        return retVal;
    }

    public boolean asBoolean() {
        if (value.equals(SRSF_NULL_STRING)) return false;
        return Boolean.parseBoolean(this.value);
    }

    public int asInt() {
        if (value.equals(SRSF_NULL_STRING)) return 0;

        return Integer.parseInt(this.value, 10);
    }

    public double asDouble() {
        if (value.equals(SRSF_NULL_STRING)) return 0;
        return Double.parseDouble(this.value);
    }

}
