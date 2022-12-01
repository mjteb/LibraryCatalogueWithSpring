package com.lb.librarycatalogue.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class JobService {

    @Scheduled(cron = "0 1 * * *")
    public void monJob() {

    }
}
