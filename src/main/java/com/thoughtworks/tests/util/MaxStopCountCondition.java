package com.thoughtworks.tests.util;

import java.util.List;

/**
 * Created by jose on 3/10/17.
 */
public class MaxStopCountCondition implements StopCondition {

    private final int maxStops;

    public MaxStopCountCondition(int maxStops) {
        this.maxStops = maxStops;
    }

    public boolean filter(List nodes) {

        //-1 because the first node is not considered a stop
        if (nodes.size() - 1 <= this.maxStops)
            return true;

        return false;
    }
}
