package com.example.demo.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByUserIdAndOfferId(Long userId, Long offerId);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByDate(LocalDate date);

    int countByDate(LocalDate date);


    List<Booking> findByStatus(String status);
}