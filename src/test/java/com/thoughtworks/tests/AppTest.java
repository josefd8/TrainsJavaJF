package com.thoughtworks.tests;

import com.thoughtworks.tests.models.Graph;
import com.thoughtworks.tests.util.FixedStopCountCondition;
import com.thoughtworks.tests.util.MaxStopCountCondition;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

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

    @Test
    public void testGetPossibleRoutesWithConditions(){

        //I believe this one is incorrect in the examples and should be 1
        assertEquals(1, graph.getRoutes("A", "C", new FixedStopCountCondition(4)).size());

        assertEquals(2, graph.getRoutes("C", "C", new MaxStopCountCondition(3)).size());

    }

    @Test
    public void testGetClosestFromSource(){

        Map<String, Double> distancesArray = new HashMap<String, Double>();
        distancesArray.put("A",new Double(1));
        distancesArray.put("B",new Double(5));
        distancesArray.put("C",new Double(6));
        distancesArray.put("D",new Double(3));
        distancesArray.put("E",new Double(4));

        List<String> unVisited = new LinkedList<String>();
        unVisited.add("B");
        unVisited.add("C");
        unVisited.add("D");
        unVisited.add("E");
        assertEquals("D", graph.getClosestFromSource(distancesArray, unVisited));

        distancesArray.put("F",new Double(0));
        unVisited.add("F");
        assertEquals("F", graph.getClosestFromSource(distancesArray, unVisited));
    }

    @Test
    public void testBestRouteCalculation(){

        assertEquals(9, graph.getShortestRouteWeight("A", "C"));

    }

}
