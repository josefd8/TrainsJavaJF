package com.thoughtworks.tests.models;

import com.thoughtworks.tests.util.StopCondition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Basic representation of a directed graph object
 * Created by jose on 2/10/17.
 */
public class Graph {

    private Map<String, List<Edge>> nodes = new HashMap<>();

    private void addNode(String vertex) {
        if (nodes.containsKey(vertex)) {
            return;
        }
        nodes.put(vertex, new ArrayList<Edge>());
    }

    public void addEdge(String sourceNode, String destinationNode, int distance) {
        this.addNode(sourceNode);
        this.addNode(destinationNode);
        nodes.get(sourceNode).add(new Edge(destinationNode, distance));
    }

    /**
     * Verify if the route submitted as an array of {{@link String}} is valid
     * For a route to be valid, there must be a continued path between the first
     * and the last element
     *
     * @param nodesArray
     * @return True if the route is valid, false otherwise
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

            if (!found)
                return found;
        }

        return true;

    }

    /**
     * Calculates the weight of a given route
     * if the route is not valid returns IndexOutOfBoundsException
     *
     * @param nodesArray
     * @return IndexOutOfBoundsException
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

            if (!found)
                throw new IndexOutOfBoundsException("Invalid route!");
        }

        return weight;

    }

    /**
     * Returns a list of all the possible routes from {sourceNode} to {destinationNode} with condition
     *
     * @param sourceNode      Name of the start node
     * @param destinationNode Name of the final node
     * @param condition       Condition to filter the resulting set of routes
     * @return List of routes that match the condition given
     */
    public List getRoutes(String sourceNode, String destinationNode, StopCondition condition) {

        LinkedList<Object> finalCount = new LinkedList<>();

        if (!sourceNode.equals(destinationNode)) {
            LinkedList<String> visited = new LinkedList();
            visited.add(sourceNode);
            finalCount = (LinkedList) this.dfs(visited, destinationNode, new LinkedList<Object>());
            return filterRoutes(finalCount, condition);
        }

        List<String> neighbours = this.getNeighbours(sourceNode);

        for (String neighbour : neighbours) {

            LinkedList<String> visited = new LinkedList();
            visited.add(neighbour);

            for (Object route : this.dfs(visited, destinationNode, new LinkedList<Object>())) {
                LinkedList<String> temp = (LinkedList<String>) route;
                temp.addFirst(sourceNode);
                finalCount.add(temp);
            }

        }

        return filterRoutes(finalCount, condition);

    }

    /**
     * Filter the given list of routes by the condition given
     *
     * @param routes    List of routes
     * @param condition Filter condition
     * @return List of routes that match the filter condition
     */
    private List filterRoutes(LinkedList<Object> routes, StopCondition condition) {

        LinkedList<Object> finalRoutes = new LinkedList<>();

        for (Object route : routes) {
            LinkedList<String> temp = (LinkedList<String>) route;

            if (condition.filter(temp)) {
                finalRoutes.add(route);
            }
        }

        return finalRoutes;
    }


    /**
     * Get the neighbours of the node
     *
     * @param node Name of the node
     * @return List of neighbour nodes
     */
    private List<String> getNeighbours(String node) {
        List<Edge> edges = this.nodes.get(node);
        if (edges == null) {
            return new LinkedList();
        }
        List<String> neighbours = new LinkedList<>();
        for (Edge edge : edges) {
            neighbours.add(edge.getVertex());
        }
        return neighbours;
    }


    /**
     * Returns a list of all the neighbour nodes of {node} that haven't yet been
     * visited
     *
     * @param node      Name of the node
     * @param unvisited List of nodes that haven't been visited
     * @return List of unvisited neighbours
     */
    private List<String> getUnvisitedNeighbours(String node, List<String> unvisited) {

        List<String> neighbours = this.getNeighbours(node);
        List<String> unVisitedNeighbours = new LinkedList<>();
        for (String n : neighbours) {
            if (unvisited.contains(n)) {
                unVisitedNeighbours.add(n);
            }
        }

        return unVisitedNeighbours;

    }

    /**
     * Performs a recursive depth search algorithm
     *
     * @param visited  List of the visited nodes
     * @param lastNode Last node evaluated
     * @param routes   List of routes from starting point to final point
     * @return List of routes from starting point to final point
     */
    private List dfs(LinkedList<String> visited, String lastNode, LinkedList<Object> routes) {
        List<String> neighbourNodes = this.getNeighbours(visited.getLast());

        for (String node : neighbourNodes) {
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
        for (String node : neighbourNodes) {
            if (visited.contains(node) || node.equals(lastNode)) {
                continue;
            }
            visited.addLast(node);
            dfs(visited, lastNode, routes);
            visited.removeLast();
        }

        return routes;

    }

    /**
     * Calculates the weight of the most efficient path from {sourceNode} to {destinationNode}
     *
     * @param sourceNode      Name of the starting node
     * @param destinationNode Name of the final node
     * @return Weight of the most efficient route
     */
    public int getShortestRouteWeight(String sourceNode, String destinationNode) {

        List<Integer> bestRoutes = new LinkedList<>();
        List<String> neighbours = this.getNeighbours(sourceNode);
        for (String neighbour : neighbours) {
            bestRoutes.add(this.getDistanceToNeighbour(sourceNode, neighbour) + this.dijkstra(neighbour, destinationNode));
        }

        Collections.sort(bestRoutes);
        return bestRoutes.get(0);
    }

    /**
     * Get the distance from {sourceNode} to its neighbour {destinationNode}
     *
     * @param sourceNode Name of the starting node
     * @param neighbour  Name of the neighbour node
     * @return Distance
     */
    private int getDistanceToNeighbour(String sourceNode, String neighbour) {

        List<Edge> edges = this.nodes.get(sourceNode);
        for (Edge e : edges) {
            if (e.getVertex().equals(neighbour)) {
                return e.getWeight();
            }
        }
        return 0;
    }

    /**
     * Performs a dijkstra (calculates route of less weight or most efficient route)
     *
     * @param sourceNode      Name of source node
     * @param destinationNode Name of final node
     * @return Weight of the most efficient route
     */
    private int dijkstra(String sourceNode, String destinationNode) {

        Map<String, Double> distanceFromSource = new HashMap<>();
        Map<String, String> previousVertex = new HashMap<>();

        List<String> unVisited = new LinkedList<>(nodes.keySet());

        for (String key : unVisited) {
            distanceFromSource.put(key, Double.POSITIVE_INFINITY);
            previousVertex.put(key, null);
        }

        distanceFromSource.put(sourceNode, (double) 0);

        while (!unVisited.isEmpty()) {

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
                    if (newCalculatedDistance < distanceFromSource.get(neighbour)) {
                        distanceFromSource.put(neighbour, newCalculatedDistance);
                        previousVertex.put(neighbour, node);
                    }
                }
            }

            unVisited.remove(node);
        }

        return distanceFromSource.get(destinationNode).intValue();
    }


    /**
     * Gets the unvisited node closest to the source node
     *
     * @param distanceFromSource Map that represent the all the nodes from the graph with its distance from source node
     * @param unVisited          List of unvisited nodes
     * @return unvisited closest node to source
     */
    private String getClosestFromSource(Map<String, Double> distanceFromSource, List<String> unVisited) {

        Set<String> keys = distanceFromSource.keySet();
        Double lowest = Double.POSITIVE_INFINITY;
        String node = unVisited.get(0);
        for (String key : keys) {
            if (unVisited.contains(key) && distanceFromSource.get(key) < lowest) {
                node = key;
                lowest = distanceFromSource.get(key);
            }
        }

        return node;

    }

    /**
     * Adds nodes and edges from file.
     *
     * @param inputFile File object to get information from
     */
    public void buildFromFile(File inputFile) throws FileNotFoundException {
        Scanner in = new Scanner(inputFile);

        try {

            while (in.hasNext()) {
                String line = in.next();

                if (line.length() != 3)
                    throw new IllegalArgumentException("One of the arguments provided is not correct: " + line);


                String startNode = String.valueOf(line.charAt(0));
                String finalNode = String.valueOf(line.charAt(1));
                int distance = Integer.parseInt(String.valueOf(line.charAt(2)));

                this.addEdge(startNode, finalNode, distance);

            }

        } finally {
            in.close();
        }

    }

}

