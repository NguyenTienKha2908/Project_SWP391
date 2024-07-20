package com.jewelry.KiraJewelry;

import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentCheck {

    private static final Logger logger = LoggerFactory.getLogger(EnvironmentCheck.class);

    @PostConstruct
    public void init() {
        logger.info("EnvironmentCheck component initialized");
        String googleCreds = System.getenv("GOOGLE_APPLICATION_CREDENTIALS");
        if (googleCreds != null) {
            logger.info("GOOGLE_APPLICATION_CREDENTIALS: " + googleCreds);
        } else {
            logger.error("GOOGLE_APPLICATION_CREDENTIALS environment variable is not set.");
        }
    }
}
