package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.OfferSlot;
import com.example.demo.Entity.Offers;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.OfferSlotRepository;

@Service
public class OfferSlotService {

    @Autowired
    private OfferSlotRepository repo;

    @Autowired
    private OfferRepository offerRepo;

    public List<OfferSlot> getSlots(Long offerId){

        Offers offer = offerRepo.findById(offerId)
                .orElseThrow(() ->
                        new RuntimeException("Offer not found: " + offerId)
                );

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(29);

       int slots = offer.getSlot();

                if(slots <= 0){
                slots = 1;
                }



        for(int i=0; i<30; i++){

            LocalDate date = today.plusDays(i);

            boolean exists = repo.existsByOfferIdAndDate(offerId, date);

            if(!exists){

                OfferSlot slot = new OfferSlot();
                slot.setOffer(offer);
                slot.setDate(date);
                slot.setRemainingSlots(slots);

                repo.save(slot);
            }
        }

        return repo.findByOfferIdAndDateBetween(
                offerId,
                today,
                endDate
        );
    }
}