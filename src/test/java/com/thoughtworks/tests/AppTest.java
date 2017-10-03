package com.thoughtworks.tests;

import org.junit.Test;
import org.junit.Before;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    Graph<String> graph;

    @Before
    public void beforeTest(){

        graph = new Graph<String>();

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addNode("D");
        graph.addNode("E");

        graph.addEdge("A", "B", 5);
        graph.addEdge("B", "C", 4);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "C", 8);
        graph.addEdge("D", "E", 6);
        graph.addEdge("A", "D", 5);
        graph.addEdge("C", "E", 2);
        graph.addEdge("E", "B", 3);
        graph.addEdge("A", "E", 7);

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

        assertTrue(graph.getRoutes("A", "B").size() == 4);
        assertTrue(graph.getRoutes("D", "E").size() == 2);


    }
}
