package com.thoughtworks.tests.models;

import com.thoughtworks.tests.util.NoStopCountCondition;
import com.thoughtworks.tests.util.StopCondition;

import java.util.*;

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
     * Calculates the weight of a given route
     * if the route is not valid returns IndexOutOfBoundsException
     *
     * @param nodesArray
     * @return
     */
    public int getRouteWeight(V[] nodesArray) {

        int weight = 0;

        for (int i = 0; i <= nodesArray.length - 2; i++) {
            if (!this.nodes.containsKey(nodesArray[i]))
                throw new IndexOutOfBoundsException("Invalid route!");

            List<Edge<V>> edges = this.nodes.get(nodesArray[i]);
            boolean found = false;
            for (Edge<V> edge : edges) {

                if (edge.getVertex().equals(nodesArray[i + 1])) {
                    weight += edge.getWeight();
                    found = true;
                    break;
                }
            }

            if (found == false)
                throw new IndexOutOfBoundsException("Invalid route!");
            ;
        }

        return weight;

    }

    /**
     * Returns a list of all the possible routes from {sourceNode} to {destinationNode}
     * @param sourceNode
     * @param destinationNode
     * @return
     */
    public List getRoutes(V sourceNode, V destinationNode) {

        return this.getRoutes(sourceNode, destinationNode, new NoStopCountCondition());
    }

    /**
     * Returns a list of all the possible routes from {sourceNode} to {destinationNode} with condition
     * @param sourceNode
     * @param destinationNode
     * @param condition
     * @return
     */
    public List getRoutes(V sourceNode, V destinationNode, StopCondition<V> condition) {

        LinkedList<V> visited = new LinkedList();
        visited.add(sourceNode);
        return this.dfs(visited, destinationNode, new LinkedList<Object>(), condition);

    }

    /**
     * Returns a list of the neighbours for the given node
     *
     * @param node
     * @return
     */
    public LinkedList<V> getNeighbours(V node) {
        List<Edge<V>> edges = this.nodes.get(node);
        if (edges == null) {
            return new LinkedList();
        }
        LinkedList<V> neighbours = new LinkedList<V>();
        for (Edge edge : edges) {
            neighbours.add((V) edge.getVertex());
        }
        return neighbours;
    }

    /**
     * Performs a depth search algorithm
     * @param visited
     * @param lastNode
     * @param routes
     * @return
     */
    private List dfs(LinkedList<V> visited, V lastNode, LinkedList<Object> routes, StopCondition<V> condition) {
        LinkedList<V> nodes = this.getNeighbours(visited.getLast());

        for (V node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(lastNode)) {
                visited.add(node);

                if (!condition.filter(visited)){
                    routes.add(new LinkedList<V>(visited));
                }

                visited.removeLast();
                break;
            }
        }
        for (V node : nodes) {
            if (visited.contains(node) || node.equals(lastNode)) {
                continue;
            }
            visited.addLast(node);
            dfs(visited, lastNode, routes, condition);
            visited.removeLast();
        }

        return routes;

    }


}

