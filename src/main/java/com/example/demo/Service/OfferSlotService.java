package com.example.demo.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // ✅ GET ALL AVAILABLE SLOTS
    @Transactional
    public List<OfferSlot> getSlots(Long offerId) {

        // ✅ FIND OFFER
        Offers offer = offerRepo.findById(offerId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Offer not found: " + offerId
                        )
                );

        LocalDate today = LocalDate.now();

        // ✅ DEFAULT SLOT COUNT
        int defaultSlots = offer.getSlot();

        

        // ✅ CREATE 30 DAYS SLOTS IF NOT EXISTS
        for (int i = 0; i < 30; i++) {

            LocalDate date = today.plusDays(i);

            boolean exists =
                    repo.existsByOfferIdAndDate(
                            offerId,
                            date
                    );

            if (!exists) {

                OfferSlot slot = new OfferSlot();

                slot.setOffer(offer);
                slot.setDate(date);

                // ✅ INITIAL SLOT VALUE
                slot.setRemainingSlots(defaultSlots);

                repo.save(slot);
            }
        }

        // ✅ RETURN AVAILABLE SLOTS
        return repo.findByOfferIdAndDateBetweenOrderByDateAsc(
                offerId,
                today,
                today.plusDays(29)
        );
    }
}