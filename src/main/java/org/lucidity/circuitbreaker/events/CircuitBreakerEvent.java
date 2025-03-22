package org.lucidity.circuitbreaker.events;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;

import java.time.Instant;

public class CircuitBreakerEvent {
    private final CircuitBreakerState oldState;
    private final CircuitBreakerState newState;
    private final Instant timestamp;

    public CircuitBreakerEvent(CircuitBreakerState oldState, CircuitBreakerState newState) {
        this.oldState = oldState;
        this.newState = newState;
        this.timestamp = Instant.now();
    }

    public CircuitBreakerState getOldState() { return oldState; }
    public CircuitBreakerState getNewState() { return newState; }
    public Instant getTimestamp() { return timestamp; }
}
