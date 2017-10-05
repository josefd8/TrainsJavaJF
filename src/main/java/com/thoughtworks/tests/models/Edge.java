package com.thoughtworks.tests.models;

/**
 * Created by jose on 2/10/17.
 */
public class Edge {

    private String vertex;
    private int weight;

    public Edge(String v, int c){
        vertex = v; weight = c;
    }

    public String getVertex() {
        return vertex;
    }

    public int getWeight() {
        return weight;
    }
}
