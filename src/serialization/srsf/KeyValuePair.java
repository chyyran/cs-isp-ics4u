package serialization.srsf;

public class KeyValuePair {
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
        return value.substring(1, value.length() - 1).split(",");
    }

    public String asString() {
        return value;
    }

    public int[] asIntArray() {
        String[] strValues = this.asStringArray();
        int[] retVal = new int[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Integer.parseInt(strValues[i]);
        }
        return retVal;
    }


    public double[] asDoubleArray() {
        String[] strValues = this.asStringArray();
        double[] retVal = new double[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
            retVal[i] = Double.parseDouble(strValues[i]);
        }
        return retVal;
    }

    public boolean[] asBooleanArray() {
        String[] strValues = this.asStringArray();
        boolean[] retVal = new boolean[strValues.length];
        for (int i = 0; i < strValues.length; i++) {
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
