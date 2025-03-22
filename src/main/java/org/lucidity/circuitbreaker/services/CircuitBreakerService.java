package org.lucidity.circuitbreaker.services;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;
import org.lucidity.circuitbreaker.events.CircuitBreakerEvent;
import org.lucidity.circuitbreaker.events.publisher.CircuitBreakerEventPublisher;
import org.lucidity.circuitbreaker.factory.CircuitBreakerStateFactory;
import org.lucidity.circuitbreaker.interfaces.CircuitBreakerStateInterface;
import org.lucidity.circuitbreaker.interfaces.impl.StateClose;

import java.util.function.Supplier;

public class CircuitBreakerService {

    private final CircuitBreakerStateFactory circuitBreakerStateFactory;// = new CircuitBreakerStateFactory(this);
    private CircuitBreakerStateInterface state;
    private final int failureThreshold;
    private final long resetTimeout;
    private final int halfOpenTestRequestCount;
    private int failureCount = 0;
    public final Supplier<String> fallback;
    private final CircuitBreakerEventPublisher eventPublisher;

    public CircuitBreakerService(int failureThreshold, long resetTimeout, int halfOpenTestRequestCount, Supplier<String> fallback, CircuitBreakerEventPublisher eventPublisher) {
        this.failureThreshold = failureThreshold;
        this.resetTimeout = resetTimeout;
        this.halfOpenTestRequestCount = halfOpenTestRequestCount;
        this.fallback = fallback;
        this.eventPublisher = eventPublisher;
        this.state = new StateClose(this);
        this.circuitBreakerStateFactory = new CircuitBreakerStateFactory(this);
    }

    public synchronized void changeState(CircuitBreakerState newState) {
        eventPublisher.publishEvent(new CircuitBreakerEvent(state.getCurrentState(), newState));
        this.state = circuitBreakerStateFactory.getState(newState);
    }

    public boolean allowRequest() {
        return state.allowRequest();
    }

    public void recordFailure() {
        state.recordFailure();
    }

    public void resetFailCounters() {
        failureCount = 0;
    }

    public void incrementFailures() {
        failureCount++;
    }

    public boolean reachedThreshold() {
        return failureCount >= failureThreshold;
    }

    public long getResetTimeout() {
        return resetTimeout;
    }

    public int getHalfOpenTestRequestCount() {
        return halfOpenTestRequestCount;
    }

    public String getFallbackResponse() {
        return state.getFallbackResponse();
    }
}
