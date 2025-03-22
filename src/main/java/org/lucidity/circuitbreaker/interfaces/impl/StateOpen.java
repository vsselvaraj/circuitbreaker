package org.lucidity.circuitbreaker.interfaces.impl;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;
import org.lucidity.circuitbreaker.interfaces.CircuitBreakerStateInterface;
import org.lucidity.circuitbreaker.services.CircuitBreakerService;

import java.time.Instant;

public class StateOpen implements CircuitBreakerStateInterface {

    private final CircuitBreakerService circuitBreaker;
    private final Instant openedAt;

    private final CircuitBreakerState state = CircuitBreakerState.OPEN;

    public StateOpen(CircuitBreakerService circuitBreaker) {
        this.circuitBreaker = circuitBreaker;
        this.openedAt = Instant.now();
    }

    @Override
    public boolean allowRequest() {
        if (Instant.now().isAfter(openedAt.plusMillis(circuitBreaker.getResetTimeout()))) {
            circuitBreaker.changeState(CircuitBreakerState.HALF_OPEN);
            return true;
        }
        return false;
    }

    @Override
    public void requestSuccess() {}

    @Override
    public void recordFailure() {}

    @Override
    public CircuitBreakerState getCurrentState(){
        return state;
    }

    @Override
    public String getFallbackResponse() {
        return circuitBreaker.fallback.get();
    }

}
