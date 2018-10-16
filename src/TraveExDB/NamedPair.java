package TraveExDB;

import javafx.util.Pair;

public class NamedPair extends Pair<String, String> {
    /**
     * Creates a new pair
     *
     * @param key   The key for this pair
     * @param value The value to use for this pair
     */
    public NamedPair(String key, String value) {
        super(key, value);
    }

    @Override
    public String toString() {
        return getValue();
    }
}
