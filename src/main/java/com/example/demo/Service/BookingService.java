package com.example.demo.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Booking;
import com.example.demo.Entity.Offers;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.LoginRepository;
import com.example.demo.Repository.OfferRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private OfferRepository offerRepo;

    // ✅ BOOK SLOT
   public Booking bookSlot(Long userId,
                        String email,
                        Long offerId,
                        String shootName,
                        String date,
                        String startTime,
                        String duration) {

    Offers offer = offerRepo.findById(offerId)
            .orElseThrow(() -> new RuntimeException("Offer not found"));

    


    
    if (offer.getSlot() <= 0) {
        throw new RuntimeException("Slot Full");
    }

   
    Booking booking = new Booking();

    booking.setUserId(userId);
    booking.setEmail(email);
    booking.setOfferId(offerId);
    booking.setShootName(shootName);

   
    booking.setDate(LocalDate.parse(date));
    booking.setStartTime(java.time.LocalTime.parse(startTime));

    // 🔥 CALCULATE END TIME FROM DURATION
   int minutes = Integer.parseInt(duration.replaceAll("[^0-9]", ""));
    booking.setEndTime(booking.getStartTime().plusMinutes(minutes));

   
    LocalDate today = LocalDate.now();

    if (booking.getDate().isBefore(today)) {
    booking.setStatus("COMPLETED");
    } else {
        booking.setStatus("UPCOMING");
    }

    booking.setCreatedAt(LocalDateTime.now());

   
    offer.setSlot(offer.getSlot() - 1);
    offerRepo.save(offer);

    return bookingRepo.save(booking);
}

   
    public List<Booking> getUserBookings(Long userId) {

        List<Booking> list = bookingRepo.findByUserId(userId);

        for (Booking b : list) {

            if (b.getDate() != null && b.getDate().isBefore(LocalDate.now())) {
                b.setStatus("COMPLETED");
            } else {
                b.setStatus("UPCOMING");
            }
        }

        return list;
    }

    // ✅ ADMIN ALL BOOKINGS
    public List<Booking> getAllBookings() {
        return bookingRepo.findAll();
    }

    // ✅ TODAY ORDERS
    public int getTodayOrders() {
        return bookingRepo.countByDate(LocalDate.now());
    }

    // ✅ TODAY INCOME
    public double getTodayIncome() {
        List<Booking> todayBookings = bookingRepo.findByDate(LocalDate.now());

        double total = 0;

        for (Booking b : todayBookings) {
            total += getOfferAmount(b.getOfferId());
        }

        return total;
    }

    // ✅ YEAR INCOME
    public double getYearIncome() {

    int currentYear = LocalDate.now().getYear();

    List<Booking> all = bookingRepo.findAll();

    double total = 0;

    for (Booking b : all) {

        if ("COMPLETED".equals(b.getStatus()) &&
            b.getDate() != null &&
            b.getDate().getYear() == currentYear) {

            total += getOfferAmount(b.getOfferId());
        }
    }

    return total;
}

    // 🔧 Helper
    private double getOfferAmount(Long offerId) {
        return offerRepo.findById(offerId)
                .map(o -> o.getAmount())
                .orElse(0.0);
    }
    @Autowired
    private LoginRepository loginRepo;

    public long getTotalUsers() {
        return loginRepo.count();
    }
}