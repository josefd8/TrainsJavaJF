package com.thoughtworks.tests.util;

/**
 * Represents a basic set of operations for a graph object.
 * Provides a basic wrapper for a consumer service
 * Created by jose on 5/10/17.
 */
public interface GraphOperationsInterface<V> {

    /**
     * Given a route represented by an array of nodes, calculates de total
     * weight of the route
     * @param nodes route (Array of node objects)
     * @return total weight of the route
     */
    int simpleRouteWeight(V[] nodes);

    /**
     * Calculates all the possible routes from {startNode} to {destinationNode}
     * @param startNode
     * @param destinationNode
     * @param condition conditio nfor calculating routes
     * @return NUmber of possible routes
     */
    int possibleRoutes(V startNode, V destinationNode, StopCondition condition);


    /**
     * Returns the weight of the most efficient route from {startNode} to {destinationNode}
     * @param startNode
     * @param destinationNode
     * @return weight of the most efficient route
     */
    int shortestRouteWeight(V startNode, V destinationNode);



}
