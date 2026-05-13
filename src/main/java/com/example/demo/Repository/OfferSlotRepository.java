package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.OfferSlot;

@Repository
public interface OfferSlotRepository
extends JpaRepository<OfferSlot, Long>{

    boolean existsByOfferIdAndDate(Long offerId, LocalDate date);

    List<OfferSlot> findByOfferIdAndDateBetweenOrderByDateAsc(
        Long offerId,
        LocalDate start,
        LocalDate end
);
    Optional<OfferSlot> findByOfferIdAndDate(
            Long offerId,
            LocalDate date
    );
}