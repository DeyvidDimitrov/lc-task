package eu.leadconcult.schoolregistry.data.entity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RateLimiter {
    // 1. Users
    // 2. Timestamp

    // 3. Map<String, Queue<long>

    private static final int MAX_REQUESTS = 5;
    private static final long TIME_WINDOW_MS = TimeUnit.MINUTES.toMillis(1); // 1 minute in milliseconds
    private Map<String, Queue<Long>> usersRequests = new ConcurrentHashMap<>();


    public RateLimiter() {
    }

    public boolean isRequestAllowed(String id) { // id = User.ID
        long currentTime = System.currentTimeMillis();
        System.out.printf("Request for ID '%s' received at %d%n", id, currentTime);

        // --- START YOUR IMPLEMENTATION HERE ---

        // 1. First req
        if (!usersRequests.containsKey(id)) {
            Queue<Long> q = new LinkedList<>();
            q.add(currentTime);
            usersRequests.put(id, q);
            return true;
        }

        // 2. Queue < MAX_REQUESTS
        if (usersRequests.get(id).size() < MAX_REQUESTS) {
            usersRequests.get(id).add(currentTime);
            return true;
        }

        // 3. Queue == MAX_REQUESTS
        if (usersRequests.get(id).peek() < currentTime - TIME_WINDOW_MS) {
            return false;
        } else {
            usersRequests.get(id).poll();
            usersRequests.get(id).add(currentTime);
            return true;
        }
        // 3.1 queue.peek.timestamp < currentTime - TIME_WINDOW_MS -> return false;
        // 3.2 else queue.pop -> put(currentTime) -> return true
        // ---
    }
}