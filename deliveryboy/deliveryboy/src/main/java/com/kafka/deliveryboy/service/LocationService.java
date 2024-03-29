package com.kafka.deliveryboy.service;

import com.kafka.deliveryboy.config.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LocationService {

    private Logger LOG = LoggerFactory.getLogger(LocationService.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public boolean updateLocation(String location) {
        this.kafkaTemplate.send(AppConstants.LOCATION_UPDATE_TOPIC, location);
        this.LOG.info("Location is Live!!");
        return true;
    }

    // Method to generate random latitude within the range [-90, 90]
    private double generateRandomLatitude() {
        Random random = new Random();
        return -90.0 + (90.0 - (-90.0)) * random.nextDouble();
    }

    // Method to generate random longitude within the range [-180, 180]
    private double generateRandomLongitude() {
        Random random = new Random();
        return -180.0 + (180.0 - (-180.0)) * random.nextDouble();
    }

    // Method to generate a random location
    public String generateRandomLocation() {
        double latitude = generateRandomLatitude();
        double longitude = generateRandomLongitude();
        return "(" + latitude + ", " + longitude + ")";
    }
}


