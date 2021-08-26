package com.bramerlabs.molecular.molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class BondMap {

    /*
    maps bonds together by atom ID
    every time an atom is added, its ID is added to the map empty:
        (id) -> (id)
    when a bond is added, two ID's are specified and the set [(id1, id2), order] is added to the bond list:
        (id1, id2, order) -> (id1, id2, order)
    list of bonds can be retrieved via the input of either id:
        id -> (id1, id2, order)
    bond order can be retrieved by the input of both ids:
        (id1, id2) -> order
    list of connected atoms can be retrieved via the input of the other id:
        id1 -> id2, id2 -> id1
     */

    /*
    the first instinct would point towards using a 2-d array as follows:
        0   1   2   3   4
        1   1   0   0   0
        2   0   0   1   1
        3   0   1   1   0
        4   0   1   0   1
    then, when input a single ID, an entire 2x column would be returned, ex 3:
        (3) ->
        1   0
        2   1
        3   1
        4   0
    and, when input a set (id1, id2), the bond order would be returned, ex (2, 3) or (3, 2):
        (2, 3) = (3, 2) -> 1
    new IDs would be added to the key row and column and then filled with 0's along the value rows and columns
    new bonds would be added by changing the value at (id1, id2):
        (id1, id2, 1) = (id2, id1, 1) -> {n => 1} where n is previous value

    this would probably be suitable in a more efficient way, but the immediately present way would be:
    a HashMap, where the key is the first ID and the value is a list of (HashMaps containing the second ID and the bond
    order):
    HashMap<Integer, ArrayList<HashMap<Integer, Integer>> map;

    A more ideal way would be to use HashMap<Integer, HashMap<Integer, Integer>>, but I don't see how this would be
    possible. This would ensure that only 1 pair (id2, order) would be allowed per id1. However, with the list method,
    new HashMaps would need to be instantiated each time a new atom is added, meaning any find() method would run in
    worst case O(n^2) time.

    the other issue is that the atom ID's are not sequential, and it wouldn't be out of the question to see atom ID's
    being only 1, 3, 5, etc. Because of this, a 2D array would not suffice either as the space complexity will scale
    with O([the highest n]^2).
     */

    public HashMap<Integer, ArrayList<Integer>> connections;
    public HashMap<Key, Integer> bondOrders;

    public BondMap(HashMap<Integer, ArrayList<Integer>> connections, HashMap<Key, Integer> bondOrders) {
        this.connections = connections;
        this.bondOrders = bondOrders;
    }

    public BondMap() {
        this.connections = new HashMap<>();
        this.bondOrders = new HashMap<>();
    }

    public void add(int id) {
        this.connections.put(id, new ArrayList<>());
    }

    public void add(int id1, int id2, int order) {
        // still runs in O(n^2) space complexity, there is likely a way to optimize this
        if (!connections.containsKey(id1)) {
            connections.put(id1, new ArrayList<>());
        }
        if (!connections.containsKey(id2)) {
            connections.put(id2, new ArrayList<>());
        }
        bondOrders.put(new Key(id1, id2), order);
        bondOrders.put(new Key(id2, id1), order);
        connections.get(id1).add(id2);
        connections.get(id2).add(id1);
    }

    public ArrayList<Integer> getConnected(int id) {
        return connections.get(id);
    }

    public int getOrder(int id1, int id2) {
        if (bondOrders.containsKey(new Key(id1, id2))) {
            return bondOrders.get(new Key(id1, id2));
        } else return bondOrders.getOrDefault(new Key(id2, id1), -1);
    }

    public ArrayList<Integer> removeAtom(int ID) {
        return connections.remove(ID);
    }

    public int removeBond(int id1, int id2) {
        return bondOrders.remove(new Key(id1, id2));
    }

    public void print() {
        connections.forEach((key, value) -> {
            System.out.print(key + ": ");
            for (Integer i : value) {
                System.out.print(i + ", ");
            }
            System.out.println();
        });
    }

    public static class Key {
        public int id1, id2;

        public Key(int id1, int id2) {
            this.id1 = id1;
            this.id2 = id2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Key)) return false;
            Key key = (Key) o;
            return id1 == key.id1 && id2 == key.id2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id1, id2);
        }
    }

}
