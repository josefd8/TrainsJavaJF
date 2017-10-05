package com.thoughtworks.tests.util;

import com.thoughtworks.tests.models.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Works as a wrapper between the most important functions exposed by a graph object and provides
 * an abstraction layer for other services that wants to make use of these functions
 * Created by jose on 5/10/17.
 */
public class GraphOperations implements GraphOperationsInterface {

    private static Graph graph;

    public GraphOperations() {}

    public GraphOperations(Graph graph) {
        this.graph = graph;
    }

    public void addEdgesFromFile(File inputFile) throws FileNotFoundException {
        this.graph = new Graph();
        this.graph.buildFromFile(inputFile);
    }

    /**
     * Adds a new edge to the graph
     * @param sourceNode        Starting node of the edge
     * @param destinationNode   Final node of the edge
     * @param distance          Distance between nodes
     */
    public void addEdge(String sourceNode, String destinationNode, int distance) {}

    /**
     * Gets the weight of a given route
     * @param nodes route (Array of node objects)
     * @return      weight of the route
     */
    public int simpleRouteWeight(String[] nodes) {

        if (graph.isValidRoute(nodes)){
            return graph.getRouteWeight(nodes);
        }

        throw new IndexOutOfBoundsException("The route is not valid!");
    }

    /**
     * Gets the nomber of possible routes from {startNode} to {destinationNode}
     * @param startNode         Name of the start node
     * @param destinationNode   Name of the final node
     * @param condition         Condition for calculating routes
     * @return                  Number of routes that match the condition
     */
    public int possibleRoutes(String startNode, String destinationNode, StopCondition condition) {

        List<Object> routes = this.graph.getRoutes(startNode, destinationNode, condition);
        return routes.size();

    }

    /**
     * Calculates the weight of the most efficient route
     * @param startNode         Start node
     * @param destinationNode   Final Node
     * @return                  Weight of the most efficient route
     */
    public int shortestRouteWeight(String startNode, String destinationNode) {

        return this.graph.getShortestRouteWeight(startNode, destinationNode);

    }
}
