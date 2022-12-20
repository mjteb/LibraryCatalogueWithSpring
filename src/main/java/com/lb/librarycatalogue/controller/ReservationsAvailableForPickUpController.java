package com.lb.librarycatalogue.controller;

import com.lb.librarycatalogue.service.ReservationsAvailableForPickUpService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservationsavailableforpickup")
public class ReservationsAvailableForPickUpController {

    private final ReservationsAvailableForPickUpService reservationsAvailableForPickUpService;

    public ReservationsAvailableForPickUpController(ReservationsAvailableForPickUpService reservationsAvailableForPickUpService) {

        this.reservationsAvailableForPickUpService = reservationsAvailableForPickUpService;
    }

}
