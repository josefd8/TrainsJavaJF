package com.thoughtworks.tests.util;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Represents a basic set of operations for a graph object.
 * Provides a basic wrapper for a consumer service
 * Created by jose on 5/10/17.
 */
public interface GraphOperationsInterface {

    /**
     * Adds nodes and edges from file.
     * @param inputFile File object to get information from
     */
    void addEdgesFromFile(File inputFile) throws FileNotFoundException;

    /**
     * Adds a new edge to the graph
     * @param sourceNode        Starting node of the edge
     * @param destinationNode   Final node of the edge
     * @param distance          Distance between nodes
     */
    void addEdge(String sourceNode, String destinationNode, int distance);

    /**
     * Given a route represented by an array of nodes, calculates de total
     * weight of the route
     * @param nodes route (Array of node objects)
     * @return total weight of the route
     */
    int simpleRouteWeight(String[] nodes);

    /**
     * Calculates all the possible routes from {startNode} to {destinationNode}
     * @param startNode
     * @param destinationNode
     * @param condition condition for calculating routes
     * @return NUmber of possible routes
     */
    int possibleRoutes(String startNode, String destinationNode, StopCondition condition);


    /**
     * Returns the weight of the most efficient route from {startNode} to {destinationNode}
     * @param startNode
     * @param destinationNode
     * @return weight of the most efficient route
     */
    int shortestRouteWeight(String startNode, String destinationNode);



}
