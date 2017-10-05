package com.thoughtworks.tests.util;

import java.util.List;

/**
 * Provides a common interface for different route conditions
 * Created by jose on 3/10/17.
 */
public interface StopCondition {

    boolean filter(List<String> route);

}
