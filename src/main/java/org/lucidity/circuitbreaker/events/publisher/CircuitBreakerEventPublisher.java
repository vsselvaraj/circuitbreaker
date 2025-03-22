package org.lucidity.circuitbreaker.events.publisher;

import org.lucidity.circuitbreaker.events.CircuitBreakerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CircuitBreakerEventPublisher {

    public CircuitBreakerEventPublisher(){

    }
    private final List<Consumer<CircuitBreakerEvent>> listeners = new ArrayList<>();

    public void addListener(Consumer<CircuitBreakerEvent> listener) {
        listeners.add(listener);
    }

    public void publishEvent(CircuitBreakerEvent event) {
        for (Consumer<CircuitBreakerEvent> listener : listeners) {
            listener.accept(event);
        }
    }
}