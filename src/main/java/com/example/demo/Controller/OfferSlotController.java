package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.OfferSlot;
import com.example.demo.Service.OfferSlotService;

@RestController
@CrossOrigin("*")
public class OfferSlotController{

    @Autowired
    private OfferSlotService service;

    @GetMapping("/slots/{offerId}")
    public List<OfferSlot> getSlots(
            @PathVariable Long offerId){

        return service.getSlots(offerId);
    }

}