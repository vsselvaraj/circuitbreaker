package org.lucidity.circuitbreaker;

import org.lucidity.circuitbreaker.events.listener.CircuitBreakerEventListener;
import org.lucidity.circuitbreaker.events.publisher.CircuitBreakerEventPublisher;
import org.lucidity.circuitbreaker.services.CircuitBreakerService;

import java.time.Duration;

public class Main {
    public static void main(String[] args) throws InterruptedException {
            CircuitBreakerEventPublisher eventPublisher = new CircuitBreakerEventPublisher();
            eventPublisher.addListener(event -> System.out.println("State changed from " + event.getOldState() + " to " + event.getNewState()));

            CircuitBreakerService circuitBreaker = new CircuitBreakerService(3, 5000, 2, () -> "Fallback response", eventPublisher);

            // Count-Based Failure - Check
            System.out.println("Test count-based failure");
            for (int i = 0; i < 5; i++) {
                if (circuitBreaker.allowRequest()) {
                    System.out.println("Request allowed.");
                    circuitBreaker.recordFailure();
                } else {
                    System.out.println("Circuit breaker is open.  " + circuitBreaker.getFallbackResponse());
                }
            }

            // Time-Based Failure Test
            System.out.println("Testing time-based failure...");
            Thread.sleep(6000); // Sleep for counters to reset
            if (circuitBreaker.allowRequest()) {
                System.out.println("Circuit breaker closed. Allowing Request.");
            } else {
                System.out.println("Circuit breaker is open. Request Blocked");
            }
        }

}