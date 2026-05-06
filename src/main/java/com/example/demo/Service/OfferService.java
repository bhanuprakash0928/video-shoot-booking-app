package com.example.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Offers;
import com.example.demo.Repository.OfferRepository;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    // GET ALL
    public List<Offers> getAllOffers() {
        return offerRepository.findAll();
    }

    // ADD
    public Offers addOffer(Offers offer) {
        return offerRepository.save(offer);
    }

    // UPDATE
    public Offers updateOffer(Long id, Offers newOffer) {

        Offers offer = offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        offer.setTitle(newOffer.getTitle());
        offer.setSmallD(newOffer.getSmallD());
        offer.setLargeD(newOffer.getLargeD());
        offer.setImage(newOffer.getImage());
        offer.setAmount(newOffer.getAmount());
        offer.setDiscount(newOffer.getDiscount());
        offer.setDuration(newOffer.getDuration());
        offer.setSlot(newOffer.getSlot());
        offer.setDate(newOffer.getDate());
        offer.setStartTime(newOffer.getStartTime());
        offer.setEndTime(newOffer.getEndTime());

        return offerRepository.save(offer);
    }

    // DELETE
    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}