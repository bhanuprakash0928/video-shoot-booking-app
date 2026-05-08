package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.OfferSlot;

@Repository
public interface OfferSlotRepository
extends JpaRepository<OfferSlot, Long>{

    boolean existsByOfferIdAndDate(
            Long offerId,
            LocalDate date
    );

    List<OfferSlot> findByOfferIdAndDateBetween(
            Long offerId,
            LocalDate start,
            LocalDate end
    );

    OfferSlot findByOfferIdAndDate(
            Long offerId,
            LocalDate date
    );

}