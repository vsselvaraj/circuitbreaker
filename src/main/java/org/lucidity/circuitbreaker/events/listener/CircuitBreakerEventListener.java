package org.lucidity.circuitbreaker.events.listener;

import org.lucidity.circuitbreaker.events.CircuitBreakerEvent;

public class CircuitBreakerEventListener {
    public void onStateChange(CircuitBreakerEvent event) {
        System.out.println("State changed from " + event.getOldState() + " to " + event.getNewState() + " at " + event.getTimestamp());
    }
}