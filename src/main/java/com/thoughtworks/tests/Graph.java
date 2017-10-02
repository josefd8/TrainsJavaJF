package com.thoughtworks.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jose on 2/10/17.
 */
public class Graph<V> {

    private Map<V, List<Edge<V>>> nodes = new HashMap<V, List<Edge<V>>>();

    public void addNode(V vertex) {
        if (nodes.containsKey(vertex)){
            return;
        }
        nodes.put(vertex, new ArrayList<Edge<V>>());
    }

    public void addEdge(V from, V to, int cost) {
        this.addNode(from);
        this.addNode(to);
        nodes.get(from).add(new Edge<V>(to, cost));
    }

    private boolean isValidRoute(V[] nodes){

        for (V v : nodes){

        }

        return false;
    }

    public int getRouteWeight(V[] nodes){

        return 0;

    }


}

