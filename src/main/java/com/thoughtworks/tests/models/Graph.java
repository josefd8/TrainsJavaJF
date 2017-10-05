package com.thoughtworks.tests.models;

import com.sun.org.apache.xerces.internal.xs.LSInputList;
import com.thoughtworks.tests.util.NoStopCountCondition;
import com.thoughtworks.tests.util.StopCondition;

import java.util.*;

/**
 * Basic representation of a directed graph object
 * Created by jose on 2/10/17.
 */
public class Graph {

    private Map<String, List<Edge>> nodes = new HashMap<String, List<Edge>>();

    public void addNode(String vertex) {
        if (nodes.containsKey(vertex)) {
            return;
        }
        nodes.put(vertex, new ArrayList<Edge>());
    }

    public void addEdge(String from, String to, int cost) {
        this.addNode(from);
        this.addNode(to);
        nodes.get(from).add(new Edge(to, cost));
    }

    /**
     * Verify if the route submitted as an array of objects of type V is valid
     * For a route to be valid, there must be a continued path between the first
     * and the last element
     *
     * @param nodesArray
     * @return
     */
    public boolean isValidRoute(String[] nodesArray) {

        if (nodesArray.length <= 1)
            return true;


        for (int i = 0; i <= nodesArray.length - 2; i++) {
            if (!this.nodes.containsKey(nodesArray[i]))
                return false;

            List<Edge> edges = this.nodes.get(nodesArray[i]);
            boolean found = false;
            for (Edge edge : edges) {

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
    public int getRouteWeight(String[] nodesArray) {

        int weight = 0;

        for (int i = 0; i <= nodesArray.length - 2; i++) {
            if (!this.nodes.containsKey(nodesArray[i]))
                throw new IndexOutOfBoundsException("Invalid route!");

            List<Edge> edges = this.nodes.get(nodesArray[i]);
            boolean found = false;
            for (Edge edge : edges) {

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
     *
     * @param sourceNode
     * @param destinationNode
     * @return
     */
    public List getRoutes(String sourceNode, String destinationNode) {

        return this.getRoutes(sourceNode, destinationNode, new NoStopCountCondition());
    }

    /**
     * Returns a list of all the possible routes from {sourceNode} to {destinationNode} with condition
     *
     * @param sourceNode
     * @param destinationNode
     * @param condition
     * @return
     */
    public List getRoutes(String sourceNode, String destinationNode, StopCondition condition) {

        LinkedList<Object> finalCount = new LinkedList<Object>();

        if (!sourceNode.equals(destinationNode)) {
            LinkedList<String> visited = new LinkedList();
            visited.add(sourceNode);
            finalCount = (LinkedList) this.dfs(visited, destinationNode, new LinkedList<Object>());
            return filterRoutes(finalCount, condition);
        }

        LinkedList<String> neighbours = this.getNeighbours(sourceNode);


        for (String neighbour : neighbours) {

            LinkedList<String> visited = new LinkedList();
            visited.add(neighbour);

            for (Object route : this.dfs(visited, destinationNode, new LinkedList<Object>())) {
                LinkedList<String> temp = new LinkedList<String>();
                temp = (LinkedList<String>) route;
                temp.addFirst(sourceNode);
                finalCount.add(temp);
            }

        }


        return filterRoutes(finalCount, condition);

    }

    /**
     * Filter the given list of routes by the condition given
     *
     * @param routes
     * @param condition
     * @return
     */
    private List filterRoutes(LinkedList<Object> routes, StopCondition condition) {

        LinkedList<Object> finalRoutes = new LinkedList<Object>();

        for (Object route : routes) {
            LinkedList<String> temp = (LinkedList<String>) route;

            if (condition.filter(temp)) {
                finalRoutes.add(route);
            }
        }

        return finalRoutes;
    }


    /**
     * Returns a list of the neighbours for the given node
     *
     * @param node
     * @return
     */
    public LinkedList<String> getNeighbours(String node) {
        List<Edge> edges = this.nodes.get(node);
        if (edges == null) {
            return new LinkedList();
        }
        LinkedList<String> neighbours = new LinkedList<String>();
        for (Edge edge : edges) {
            neighbours.add( edge.getVertex());
        }
        return neighbours;
    }

    private List<String> getUnvisitedNeighbours(String node, List<String> unvisited){

        List<String> neighbours = this.getNeighbours(node);
        List<String> unVisitedNeighbours = new LinkedList<String>();
        for(String n : neighbours){
            if (unvisited.contains(n)){
                unVisitedNeighbours.add(n);
            }
        }

        return unVisitedNeighbours;

    }

    /**
     * Performs a depth search algorithm
     *
     * @param visited
     * @param lastNode
     * @param routes
     * @return
     */
    private List dfs(LinkedList<String> visited, String lastNode, LinkedList<Object> routes) {
        LinkedList<String> nodes = this.getNeighbours(visited.getLast());

        for (String node : nodes) {
            if (visited.contains(node)) {
                continue;
            }
            if (node.equals(lastNode)) {
                visited.add(node);
                routes.add(new LinkedList<String>(visited));
                visited.removeLast();
                break;
            }
        }
        for (String node : nodes) {
            if (visited.contains(node) || node.equals(lastNode)) {
                continue;
            }
            visited.addLast(node);
            dfs(visited, lastNode, routes);
            visited.removeLast();
        }

        return routes;

    }

    public int getShortestRouteWeight(String sourceNode, String destinationNode) {

        List<Integer> bestRoutes = new LinkedList<Integer>();
        List<String> neighbours = this.getNeighbours(sourceNode);
        for (String neighbour : neighbours){
            bestRoutes.add(this.getDistanceToNeighbour(sourceNode, neighbour) + this.dijkstra(neighbour, destinationNode));
        }

        Collections.sort(bestRoutes);
        return bestRoutes.get(0);
    }

    private int getDistanceToNeighbour(String sourceNode, String neighbour){

        List<Edge> edges = this.nodes.get(sourceNode);
            for (Edge e : edges){
                if (e.getVertex().equals(neighbour)){
                    return e.getWeight();
                }
            }
        return 0;
    }



    public int dijkstra(String sourceNode, String destinationNode) {

        Map<String, Double> distanceFromSource = new HashMap<String, Double>();
        Map<String, String> previousVertex = new HashMap<String, String>();

        List<String> unVisited = new LinkedList<String>(nodes.keySet());

        for (String key : unVisited) {
            distanceFromSource.put(key, Double.POSITIVE_INFINITY);
            previousVertex.put(key, null);
        }

        distanceFromSource.put(sourceNode, new Double(0));

        while (unVisited.size() > 0) {

            //Get unVisited node with the lowest distance from sourceNode
            String node = getClosestFromSource(distanceFromSource, unVisited);

            //Get unvisited neighbours from that node
            List<String> neighbours = this.getUnvisitedNeighbours(node, unVisited);

            for (String neighbour : neighbours) {

                //Analize the un visited neighbours
                if (unVisited.contains(neighbour)) {

                    //Calculate distance from node to its neighbour
                    int distance = this.getDistanceToNeighbour(node, neighbour);

                    Double newCalculatedDistance = distance + distanceFromSource.get(node);
                    if (newCalculatedDistance < distanceFromSource.get(neighbour)){
                        distanceFromSource.put(neighbour, newCalculatedDistance);
                        previousVertex.put(neighbour, node);
                    }
                }
            }

            unVisited.remove(node);
        }

        return distanceFromSource.get(destinationNode).intValue();
    }


    public String getClosestFromSource(Map<String, Double> distanceFromSource, List<String> unVisited) {

        Set<String> keys = distanceFromSource.keySet();
        Double lowest = Double.POSITIVE_INFINITY;
        String node = unVisited.get(0);
        for (String key : keys) {
            if (unVisited.contains(key)){
                if (distanceFromSource.get(key) < lowest) {
                    node = key;
                    lowest = distanceFromSource.get(key);
                }
            }
        }

        return node;

    }

}

