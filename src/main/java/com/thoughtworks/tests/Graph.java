package com.thoughtworks.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Basic representation of a graph object
 * Created by jose on 2/10/17.
 */
public class Graph<V> {

    private Map<V, List<Edge<V>>> nodes = new HashMap<V, List<Edge<V>>>();

    public void addNode(V vertex) {
        if (nodes.containsKey(vertex)) {
            return;
        }
        nodes.put(vertex, new ArrayList<Edge<V>>());
    }

    public void addEdge(V from, V to, int cost) {
        this.addNode(from);
        this.addNode(to);
        nodes.get(from).add(new Edge<V>(to, cost));
    }

    /**
     * Verify if the route submitted as an array of objects of type V is valid
     * For a route to be valid, there must be a continued path between the first
     * and the last element
     *
     * @param nodesArray
     * @return
     */
    public boolean isValidRoute(V[] nodesArray) {

        if (nodesArray.length <= 1)
            return true;


        for (int i = 0; i <= nodesArray.length - 2; i++) {
            if (!this.nodes.containsKey(nodesArray[i]))
                return false;

            List<Edge<V>> edges = this.nodes.get(nodesArray[i]);
            boolean found = false;
            for (Edge<V> edge : edges) {

                if (edge.getVertex().equals(nodesArray[i + 1])) {
                    found = true;
                    break;
                }
            }

            if (found == false)
                return false;
        }

        return true;

    }

    /**
     * @param nodes
     * @return
     */
    public int getRouteWeight(V[] nodes) {

        return 0;

    }


}

