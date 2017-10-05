package com.thoughtworks.tests;

import com.thoughtworks.tests.models.Graph;
import com.thoughtworks.tests.util.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Unit test for simple Main.
 */
public class MainTest
{
    Graph graph;
    GraphOperationsInterface operationService;

    @Before
    public void beforeTest(){

        graph = new Graph();

        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "C", 8);
        graph.addEdge("D", "E", 6);
        graph.addEdge("A", "D", 5);
        graph.addEdge("C", "E", 2);
        graph.addEdge("E", "B", 3);
        graph.addEdge("A", "E", 7);

        this.operationService = new GraphOperations(this.graph);


    }

    @Test
    public void testValidRoute(){

        assertTrue(graph.isValidRoute(new String[]{"A", "B", "C"}));
        assertTrue(graph.isValidRoute(new String[]{"A", "D"}));
        assertTrue(graph.isValidRoute(new String[]{"A", "D", "C"}));
        assertTrue(graph.isValidRoute(new String[]{"A", "E", "B", "C", "D"}));
        assertTrue(graph.isValidRoute(new String[]{"A", "E", "B", "C", "D", "C", "E"}));
        assertFalse(graph.isValidRoute(new String[]{"A", "E", "D"}));
    }

    @Test
    public void testSimpleRouteWeightCalculation()
    {
        assertTrue(graph.getRouteWeight(new String[]{"A", "B", "C"}) == 9);
        assertTrue(graph.getRouteWeight(new String[]{"A", "D"}) == 5);
        assertTrue(graph.getRouteWeight(new String[]{"A", "D", "C"}) == 13);
        assertTrue(graph.getRouteWeight(new String[]{"A", "E", "B", "C", "D"}) == 22);
    }

    @Test(expected = IndexOutOfBoundsException.class)
        public void testInvalidRouteWeightCalculation()
    {
        assertTrue(graph.getRouteWeight(new String[]{"A", "E", "D"}) == 9);
        assertTrue(graph.getRouteWeight(new String[]{"A", "B", "E"}) == 10);

    }

    @Test
    public void testGetPossibleRoutes(){
        assertTrue(graph.getRoutes("A", "B", new NoStopCountCondition()).size() == 4);
        assertTrue(graph.getRoutes("D", "E", new NoStopCountCondition()).size() == 2);
    }

    @Test
    public void testGetPossibleRoutesWithConditions(){

        //I believe this one is incorrect in the examples and should be 1
        assertEquals(1, graph.getRoutes("A", "C", new FixedStopCountCondition(4)).size());

        assertEquals(2, graph.getRoutes("C", "C", new MaxStopCountCondition(3)).size());
    }

    @Test
    public void testBestRouteCalculation(){
        assertEquals(9, graph.getShortestRouteWeight("A", "C"));
        assertEquals(9, graph.getShortestRouteWeight("B", "B"));
    }

    @Test
    public void testSimpleRouteWeight(){

        assertEquals(9, operationService.simpleRouteWeight(new String[]{"A", "B", "C"}));
        assertEquals(5, operationService.simpleRouteWeight(new String[]{"A", "D"}));
        assertEquals(13, operationService.simpleRouteWeight(new String[]{"A", "D", "C"}));
        assertEquals(22, operationService.simpleRouteWeight(new String[]{"A", "E", "B", "C", "D"}));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testSimpleRouteWeightNoValidRoute(){
        assertEquals(0, this.operationService.simpleRouteWeight(new String[]{"A", "E", "D"}));
    }

    @Test
    public void testPossibleRoutes(){

        assertEquals(4, this.operationService.possibleRoutes("A", "B", new NoStopCountCondition()));
        assertEquals(2, this.operationService.possibleRoutes("D", "E", new NoStopCountCondition()));
        assertEquals(0, this.operationService.possibleRoutes("B", "A", new NoStopCountCondition()));
        assertEquals(1, this.operationService.possibleRoutes("A", "C", new FixedStopCountCondition(4)));
        assertEquals(2, this.operationService.possibleRoutes("C", "C", new MaxStopCountCondition(3)));

    }

    @Test
    public void testShortestRouteWeight(){
        assertEquals(9, this.operationService.shortestRouteWeight("A", "C"));
        assertEquals(9, this.operationService.shortestRouteWeight("B", "B"));
    }

}
