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

        if (nodes.size() - 1 <= this.maxStops)
            return true;

        return false;
    }
}
