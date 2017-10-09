package com.thoughtworks.tests;

import com.thoughtworks.tests.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Main entry point of the application
 * Builds a directed graph object using the information in the routes.txt file
 * Performs the operations in the operations.txt in the previously built graph
 *
 * @author Jose Fernandez
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        GraphOperationsInterface operationService = new GraphOperations();
        File nodesFiles = new File("src/main/resources/routes.txt");
        operationService.addEdgesFromFile(nodesFiles);


        File operationsFile = new File("src/main/resources/operations.txt");
        Scanner in = new Scanner(operationsFile);

        try {

            while (in.hasNext()) {
                String line = in.next();

                String[] parameters = line.split("\\*");

                if (parameters[0].matches("distanceOf")) {
                    try {
                        System.out.print(operationService.simpleRouteWeight(getRoute(parameters[1])));
                    } catch (IndexOutOfBoundsException e) {
                        System.out.print("NO SUCH ROUTE");
                    }

                }

                if (parameters[0].matches("numberOfRoutes")) {
                    try {
                        String[] nodes = getRoute(parameters[1]);
                        int distance;

                        if (parameters[2].matches("NoStopCondition")) {
                            System.out.print(operationService.possibleRoutes(nodes[0], nodes[1], new NoStopCountCondition()));
                        }

                        if (parameters[2].matches("MaxStopCondition")) {
                            distance = Integer.parseInt(String.valueOf(parameters[3]));
                            System.out.print(operationService.possibleRoutes(nodes[0], nodes[1], new MaxStopCountCondition(distance)));
                        }

                        if (parameters[2].matches("FixedStopCondition")) {
                            distance = Integer.parseInt(String.valueOf(parameters[3]));
                            System.out.print(operationService.possibleRoutes(nodes[0], nodes[1], new FixedStopCountCondition(distance)));
                        }


                    } catch (IndexOutOfBoundsException e) {
                        System.out.print("NO SUCH ROUTE");
                    }
                }


                if (parameters[0].matches("bestRoute")) {
                    String[] nodes = getRoute(parameters[1]);
                    System.out.print(operationService.shortestRouteWeight(nodes[0], nodes[1]));
                }

                System.out.print("\n");
            }

        } finally {
            in.close();
        }


        System.out.print("Finnish!");


    }

    private static String[] getRoute(String route) {
        route = route.replace("[", "");
        route = route.replace("]", "");
        return route.split(",");
    }
}
