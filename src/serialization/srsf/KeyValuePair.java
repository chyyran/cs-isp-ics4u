package serialization.srsf;

public class KeyValuePair {
    private final String value;
    private final String key;
    private final static String NULL_STRING = "@@NUL@@";
    public KeyValuePair(String key, String value) {
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

    public String[] asStringArray() {
        if (value.equals(NULL_STRING)) return null;
        return value.substring(1, value.length() - 1).split(",");
    }

    public String asString() {
        if (value.equals(NULL_STRING)) return null;
        return value;
    }

    public int[] asIntArray() {
        if (value.equals(NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        int[] retVal = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Integer.parseInt(strValues[i]);
        }
        return retVal;
    }


    public double[] asDoubleArray() {
        if (value.equals(NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        double[] retVal = new double[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Double.parseDouble(strValues[i]);
        }
        return retVal;
    }

    public boolean[] asBooleanArray() {
        if (value.equals(NULL_STRING)) return null;
        String[] strValues = this.asStringArray();
        boolean[] retVal = new boolean[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Boolean.parseBoolean(strValues[i]);
        }
        return retVal;
    }

    public boolean asBoolean() {
        if (value.equals(NULL_STRING)) return false;
        return Boolean.parseBoolean(this.value);
    }

    public int asInt() {
        if (value.equals(NULL_STRING)) return 0;

        return Integer.parseInt(this.value, 10);
    }

    public double asDouble() {
        if (value.equals(NULL_STRING)) return 0;
        return Double.parseDouble(this.value);
    }

}
