package com.thoughtworks.tests;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;

import java.util.InvalidPropertiesFormatException;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase
{
    Graph<String> graph = new Graph<String>();

    @Before
    public void beforeTest(){

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

    }
}
