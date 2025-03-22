package org.lucidity.circuitbreaker.interfaces.impl;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;
import org.lucidity.circuitbreaker.interfaces.CircuitBreakerStateInterface;
import org.lucidity.circuitbreaker.services.CircuitBreakerService;

public class StateHalfOpen implements CircuitBreakerStateInterface {

        private final CircuitBreakerState state = CircuitBreakerState.HALF_OPEN;
        private final CircuitBreakerService circuitBreaker;
        private int successCount = 0;

        public StateHalfOpen(CircuitBreakerService circuitBreaker) {
            this.circuitBreaker = circuitBreaker;
        }

        @Override
        public boolean allowRequest() {
            return true;
        }

        @Override
        public void requestSuccess() {
            successCount++;
            if (successCount >= circuitBreaker.getHalfOpenTestRequestCount()) {
                circuitBreaker.changeState(CircuitBreakerState.CLOSED);
            }
        }

        @Override
        public void recordFailure() {
            circuitBreaker.changeState(CircuitBreakerState.OPEN);
        }

    @Override
    public CircuitBreakerState getCurrentState() {
        return CircuitBreakerState.HALF_OPEN;
    }

    @Override
    public String getFallbackResponse() {
        return circuitBreaker.fallback.get();
    }

}
