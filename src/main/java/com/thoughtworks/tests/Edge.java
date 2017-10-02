package com.thoughtworks.tests;

/**
 * Created by jose on 2/10/17.
 */
public class Edge<V> {

    private V vertex;
    private int weight;

    public Edge(V v, int c){
        vertex = v; weight = c;
    }

    public V getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }
}
