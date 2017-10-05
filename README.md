# TrainsJavaJF


To use the project:

 - Unzip the file TrainsJavaJF.zip
 - cd to folder: cd /TrainsJavaJF
 - mvn package
 - execute the resulting jar as

> java -jar target/train-1.0-SNAPSHOT.jar

You can use the file located at **/resources/routes.txt** to load the information of the towns and the distance between towns. This information is used to create a graph object

The file located at **/resources/operations** contains all the different operations that are gonna be executed on the graph object (routes calculation, best route, etc)

Each line represents a single operation, and the different parameters are separated by the "*" character

There are 3 basic operation that can be performed:


----------


**distanceOf:** calculates the distance (weight) of the given route. The route is a list of Strings separated by commas and enclosed in "[" and "]" characters. So, for example, the route A->B->C is represented as [A,B,C]
Example: distanceOf*[A,B,C]


----------


**numberOfRoutes:** Calculates the number of all possible routes from the start node to the final node that matches the given condition

The start and final node are represented as [start,final] (i.e [C,C])

The condition to filter the routes is represented by a String and the possible values are:

 - NoStopCondition: No filtering is made on the resulting routes
 - MaxStopCondition: Returns the number of routes with an specific maximum number of stops
 - FixedStopCondition: Returns the number of routes that have an exact number of stops

Examples:

Return the number of all the posible routes from C to C

> numberOfRoutes*[C,C]*NoStopCondition

Return the number of all the possible routes from C to C with a maximum of 3 stops

> numberOfRoutes*[C,C]*MaxStopCondition*3

Return the number of all the posible routes from A to C that have exactly 4 stops

> numberOfRoutes*[A,C]*FixedStopCondition*4


----------


**bestRoute:** The length of the shortest route (in terms of distance to travel) from the given nodes

The start and final node are represented as [start,final] (i.e [C,C])

Example

> bestRoute*[A,C]