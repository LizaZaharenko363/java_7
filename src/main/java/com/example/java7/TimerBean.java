package com.example.java7;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import java.util.logging.Logger;

@Singleton
@Startup
public class TimerBean {

    private static final Logger logger = Logger.getLogger(TimerBean.class.getName());

    @Schedule(hour = "*", minute = "*", second = "0", persistent = false)
    public void scheduledMethod() {
        logger.info("Timer triggered! Message printed to the console.");
    }
}
