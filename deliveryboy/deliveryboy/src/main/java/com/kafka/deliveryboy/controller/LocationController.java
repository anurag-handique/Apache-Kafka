package com.kafka.deliveryboy.controller;

import com.kafka.deliveryboy.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationService kafkaService;

    // ExecutorService to manage threads for parallel execution
    private ExecutorService executorService = Executors.newFixedThreadPool(10); // Adjust thread pool size as needed

    @PostMapping("/update")
    public ResponseEntity<?> updateLocation() {
        int numberOfLocations = 200000; // Number of locations to generate and update

        for (int i = 0; i < numberOfLocations; i++) {
            executorService.execute(() -> {
                String randomLocation = kafkaService.generateRandomLocation();
                kafkaService.updateLocation(randomLocation);
            });
        }

        // Shut down the executor service after all tasks are submitted
        executorService.shutdown();

        try {
            // Wait for all tasks to complete or timeout after a certain duration
            executorService.awaitTermination(1, TimeUnit.HOURS); // Adjust timeout duration as needed
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Check if all tasks completed successfully
        if (executorService.isTerminated()) {
            return ResponseEntity.ok().build(); // Return 200 OK if all updates are successful
        } else {
            // Return 500 Internal Server Error if some updates failed
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
