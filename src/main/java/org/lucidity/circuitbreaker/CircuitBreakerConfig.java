package org.lucidity.circuitbreaker;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class CircuitBreakerConfig {

    private final int MAX_THRESHOLD = 10;
    private final Duration MAX_DURATION = Duration.ofSeconds(10);
    private int failureThreshold;
    private boolean isTimeBased;
    private Duration timeWindow;

    public CircuitBreakerConfig(boolean isTimeBased){
        this.failureThreshold = MAX_THRESHOLD;
        this.timeWindow =MAX_DURATION;
        this.isTimeBased = isTimeBased;
    }

    public void setFailureThreshold(int failureThreshold) {
        this.failureThreshold = Math.min(failureThreshold, MAX_THRESHOLD);
    }

    public void setTimeWindow(Duration timeWindow){
        if(!timeWindow.isZero()){
           // this.timeWindow = timeWindow;
            this.timeWindow = Duration.ofSeconds(Math.min(timeWindow.get(ChronoUnit.SECONDS), MAX_DURATION.get(ChronoUnit.SECONDS)));
        }
    }

    public void setTimeBased(boolean isTimeBased){
        this.isTimeBased = isTimeBased;
    }
}
