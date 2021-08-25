package com.bramerlabs.molecular.engine3D.math.map;

import java.util.HashMap;
import java.util.Map;

public class BiHashMap <K1, K2, V> {
    private final Map<K1, Map<K2, V>> bimap;

    public BiHashMap() {
        bimap = new HashMap<>();
    }

    /**
     *
     * @param key1 the first key
     * @param key2 the second key
     * @param value the value to be set
     * @return the value previously associated with (key1, key2),
     * @see Map#put(Object, Object)
     */
    public V put (K1 key1, K2 key2, V value) {
        Map<K2, V> map;
        if (bimap.containsKey(key1)) {
            map = bimap.get(key1);
        } else {
            map = new HashMap<>();
            bimap.put(key1, map);
        }
        return map.put(key2, value);
    }

    /**
     *
     * @param key1 the first key
     * @param key2 the second key
     * @return the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for
     * the key
     * @see Map#get(Object)
     */
    public V get(K1 key1, K2 key2) {
        if (bimap.containsKey(key1)) {
            return bimap.get(key1).get(key2);
        }
        return null;
    }

    /**
     *
     * @param key1 the first key to detect
     * @param key2 the second key to detect
     * @return true if this map contains the key set <code>(key1, key2)</code>
     * @see Map#containsKey(Object)
     */
    public boolean containsKeys(K1 key1, K2 key2) {
        return bimap.containsKey(key1) && bimap.get(key1).containsKey(key2);
    }

    /**
     * clears the map
     * @see Map#clear()
     */
    public void clear() {
        bimap.clear();
    }

}
