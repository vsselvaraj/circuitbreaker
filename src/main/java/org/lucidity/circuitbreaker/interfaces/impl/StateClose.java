package org.lucidity.circuitbreaker.interfaces.impl;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;
import org.lucidity.circuitbreaker.interfaces.CircuitBreakerStateInterface;
import org.lucidity.circuitbreaker.services.CircuitBreakerService;

public class StateClose implements CircuitBreakerStateInterface {
        private final CircuitBreakerService circuitBreaker;

        private final CircuitBreakerState state = CircuitBreakerState.CLOSED;

        public StateClose(CircuitBreakerService circuitBreaker) {
            this.circuitBreaker = circuitBreaker;
        }

        @Override
        public boolean allowRequest() {
            return true;
        }

        @Override
        public void requestSuccess() {
            circuitBreaker.resetFailCounters();
        }

        @Override
        public void recordFailure() {
            circuitBreaker.incrementFailures();
            if (circuitBreaker.reachedThreshold()) {
                circuitBreaker.changeState(CircuitBreakerState.OPEN);
            }
        }

        @Override
        public CircuitBreakerState getCurrentState(){
            return state;
        }

    @Override
    public String getFallbackResponse() {
        return circuitBreaker.fallback.get();
    }

}
