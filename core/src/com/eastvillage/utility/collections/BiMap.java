package com.eastvillage.utility.collections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/** The BiMap is a bi-directional map such that if a key is mapped to a value, that value is also mapped
 * to that key. Use the inv() method to retrieve the inverted map. */
public class BiMap<K, V> implements Map<K, V> {

    private HashMap<K, V> one;
    private HashMap<V, K> two;

    public BiMap() {
        one = new HashMap<>();
        two = new HashMap<>();
    }

    /** Construct a clone of a BiMap. */
    public BiMap(BiMap<K, V> biMap) {
        this(new HashMap<>(biMap.one), new HashMap<>(biMap.two));
    }

    private BiMap(HashMap<K, V> one, HashMap<V, K> two) {
        this.one = one;
        this.two = two;
    }

    /** Invert the bi-directinal map to use in the other direction.
     * @return an identical map, but the keys and values have been swapped. */
    public BiMap<V, K> inv() {
        return new BiMap<>(two, one);
    }

    @Override
    public int size() {
        return one.size();
    }

    @Override
    public boolean isEmpty() {
        return one.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return one.containsKey(key) || two.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return one.containsValue(value) || two.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return one.get(key);
    }

    @Override
    public V put(K key, V value) {
        V prevValue = one.get(key);
        K prevKey = two.get(value);

        one.put(prevKey, null);
        two.put(prevValue, null);

        one.put(key, value);
        two.put(value, key);

        return prevValue;
    }

    @Override
    public V remove(Object key) {
        V prev = one.remove(key);
        if (prev != null) two.remove(prev);
        return prev;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (K key : m.keySet()) {
            V val = m.get(key);
            put(key, val);
        }
    }

    @Override
    public void clear() {
        one.clear();
        two.clear();
    }

    @Override
    public Set<K> keySet() {
        return one.keySet();
    }

    @Override
    public Collection<V> values() {
        return one.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return one.entrySet();
    }
}
