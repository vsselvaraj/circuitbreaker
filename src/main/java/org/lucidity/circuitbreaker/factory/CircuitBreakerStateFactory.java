package org.lucidity.circuitbreaker.factory;

import org.lucidity.circuitbreaker.enums.CircuitBreakerState;
import org.lucidity.circuitbreaker.interfaces.CircuitBreakerStateInterface;
import org.lucidity.circuitbreaker.interfaces.impl.StateClose;
import org.lucidity.circuitbreaker.interfaces.impl.StateHalfOpen;
import org.lucidity.circuitbreaker.interfaces.impl.StateOpen;
import org.lucidity.circuitbreaker.services.CircuitBreakerService;

import java.util.HashMap;
import java.util.Map;

public class CircuitBreakerStateFactory {

    private final Map<CircuitBreakerState, CircuitBreakerStateInterface> stateInstances = new HashMap<>();

    private CircuitBreakerStateInterface currentState;
    private CircuitBreakerService circuitBreakerService;
    public CircuitBreakerStateFactory(CircuitBreakerService circuitBreakerService) {

        stateInstances.put(CircuitBreakerState.CLOSED, new StateClose(circuitBreakerService));
        stateInstances.put(CircuitBreakerState.OPEN, new StateOpen(circuitBreakerService));
        stateInstances.put(CircuitBreakerState.HALF_OPEN, new StateHalfOpen(circuitBreakerService));

    }

    public CircuitBreakerStateInterface getState(CircuitBreakerState state) {
        return stateInstances.get(state);
    }
    }