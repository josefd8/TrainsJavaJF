package com.thoughtworks.tests.util;

import java.util.List;

/**
 * Created by jose on 3/10/17.
 */
public class NoStopCountCondition implements StopCondition {
    public boolean filter(List route) {
        return true;
    }
}
