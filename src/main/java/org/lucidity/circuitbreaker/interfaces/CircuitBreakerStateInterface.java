package org.lucidity.circuitbreaker.interfaces;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;

public interface CircuitBreakerStateInterface {

    public boolean allowRequest();

    public void requestSuccess() ;

    public void recordFailure();

    public CircuitBreakerState getCurrentState();

    String getFallbackResponse();
}
