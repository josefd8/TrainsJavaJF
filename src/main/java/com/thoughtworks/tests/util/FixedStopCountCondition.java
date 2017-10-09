package com.thoughtworks.tests.util;

import java.util.List;

/**
 * Created by jose on 3/10/17.
 */
public class FixedStopCountCondition implements StopCondition {

    private final int stops;

    public FixedStopCountCondition(int maxStops) {
        this.stops = maxStops;
    }

    public boolean filter(List nodes) {

        //-1 because the first node is not considered a stop
        return (nodes.size() - 1 == this.stops);

    }
}
