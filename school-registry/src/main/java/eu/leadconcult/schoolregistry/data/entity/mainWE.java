package eu.leadconcult.schoolregistry.data.entity;

import java.util.concurrent.TimeUnit;

public class mainWE {
    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW_MS = TimeUnit.MINUTES.toMillis(1); // 1 minute in milliseconds

    // Optional: Main method for quick testing
    public static void main(String[] args) throws InterruptedException {
        RateLimiter limiter = new RateLimiter();
        String testId = "user123";

        System.out.printf("Simulating requests for ID: %s%n", testId);
        System.out.printf("Limit: %d requests per %d seconds.%n", MAX_REQUESTS, TimeUnit.MILLISECONDS.toSeconds(TIME_WINDOW_MS));
        System.out.println("---");

        for (int i = 1; i <= 7; i++) {
            boolean allowed = limiter.isRequestAllowed(testId);
            System.out.printf("Request %d: Allowed = %b%n", i, allowed);
            if (i < MAX_REQUESTS + 1) { // Simulate some quick requests
                Thread.sleep(200); // Small delay between initial requests
            } else if (i == MAX_REQUESTS + 1) { // After hitting the limit, wait a bit
                System.out.println("Waiting for 10 seconds before next request...");
                Thread.sleep(TimeUnit.SECONDS.toMillis(10));
            }
        }

        System.out.println("\n--- Waiting for window to partially slide ---");
        long partialWaitSeconds = (TimeUnit.MILLISECONDS.toSeconds(TIME_WINDOW_MS) / 2) + 1;
        System.out.printf("Waiting for %d seconds...%n", partialWaitSeconds);
        Thread.sleep(TimeUnit.SECONDS.toMillis(partialWaitSeconds));

        boolean afterWaitAllowed = limiter.isRequestAllowed(testId);
        System.out.printf("Request after partial wait: Allowed = %b%n", afterWaitAllowed);

        System.out.println("\n--- Waiting for window to fully slide ---");
        long fullWaitSeconds = TimeUnit.MILLISECONDS.toSeconds(TIME_WINDOW_MS) + 1;
        // The previous wait was 10s + (approx 30s).
        // To ensure first requests are out of window, wait for the full window duration from "now".
        System.out.printf("Waiting for %d seconds (from the time of the first few requests)...%n", fullWaitSeconds);
        Thread.sleep(TimeUnit.SECONDS.toMillis(fullWaitSeconds));


        afterWaitAllowed = limiter.isRequestAllowed(testId);
        System.out.printf("Request after full wait: Allowed = %b%n", afterWaitAllowed);

        System.out.println("\n--- Testing a different ID ---");
        String testId2 = "user456";
        for (int i = 1; i <= 3; i++) {
            boolean allowed = limiter.isRequestAllowed(testId2);
            System.out.printf("Request %d for ID '%s': Allowed = %b%n", i, testId2, allowed);
            Thread.sleep(200);
        }

        System.out.println("\nDone.");
    }
}
