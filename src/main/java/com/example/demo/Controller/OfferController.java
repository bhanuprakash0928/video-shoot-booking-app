package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Offers;
import com.example.demo.Service.OfferService;

@RestController
@RequestMapping("/offers")
@CrossOrigin("*")
public class OfferController {

    @Autowired
    private OfferService offerService;

    // GET ALL OFFERS
    @GetMapping
    public List<Offers> getAllOffers() {
        return offerService.getAllOffers();
    }

    // ADD OFFER
    @PostMapping("/add")
    public Offers addOffer(@RequestBody Offers offer) {
        
    return offerService.addOffer(offer);
    }

    // UPDATE OFFER
    @PutMapping("/update/{id}")
    public Offers updateOffer(@PathVariable Long id,
                              @RequestBody Offers newOffer) {
        return offerService.updateOffer(id, newOffer);
    }

    // DELETE OFFER
    @DeleteMapping("/delete/{id}")
    public String deleteOffer(@PathVariable Long id) {
        offerService.deleteOffer(id);
        return "Deleted Successfully";
    }
}