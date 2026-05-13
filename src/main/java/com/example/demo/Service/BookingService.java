package com.example.demo.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Booking;
import com.example.demo.Entity.OfferSlot;
import com.example.demo.Entity.Offers;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Repository.LoginRepository;
import com.example.demo.Repository.OfferRepository;
import com.example.demo.Repository.OfferSlotRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepo;

    @Autowired
    private OfferRepository offerRepo;

    @Autowired
    private OfferSlotRepository slotRepo;

    @Autowired
    private LoginRepository loginRepo;

    // ✅ BOOK SLOT
    @Transactional
    public Booking bookSlot(
            Long userId,
            String email,
            Long offerId,
            String shootName,
            String date,
            String startTime,
            String duration
    ) {

        // ✅ FIND OFFER
        Offers offer = offerRepo.findById(offerId)
                .orElseThrow(() ->
                        new RuntimeException("Offer not found")
                );

        // ✅ CREATE BOOKING OBJECT
        Booking booking = new Booking();

        booking.setUserId(userId);
        booking.setEmail(email);
        booking.setOfferId(offerId);
        booking.setShootName(shootName);

        // ✅ DATE
        LocalDate bookingDate = LocalDate.parse(date);
        booking.setDate(bookingDate);

        // ✅ START TIME
        booking.setStartTime(
                java.time.LocalTime.parse(startTime)
        );

        // ✅ DURATION → END TIME
        int hours = 1;

        try {

            // Example:
            // "2:1" → 2
            // "3 Hours" → 3

            if (duration.contains(":")) {

                hours = Integer.parseInt(
                        duration.split(":")[0]
                );

            } else {

                hours = Integer.parseInt(
                        duration.replaceAll("[^0-9]", "")
                );
            }

        } catch (Exception e) {

            hours = 1;
        }

        booking.setEndTime(
                booking.getStartTime().plusHours(hours)
        );

        // ✅ STATUS
        LocalDate today = LocalDate.now();

        if (bookingDate.isBefore(today)) {
            booking.setStatus("COMPLETED");
        } else {
            booking.setStatus("UPCOMING");
        }

        booking.setCreatedAt(LocalDateTime.now());

        // ✅ FIND SLOT OR CREATE SLOT
        OfferSlot slot = slotRepo.findByOfferIdAndDate(
                offerId,
                bookingDate
        ).orElseGet(() -> {

            OfferSlot newSlot = new OfferSlot();

            newSlot.setOffer(offer);
            newSlot.setDate(bookingDate);

            int defaultSlots = offer.getSlot();

            if (defaultSlots <= 0) {
                defaultSlots = 1;
            }

            newSlot.setRemainingSlots(defaultSlots);

            return slotRepo.save(newSlot);
        });

        // ✅ CHECK SLOT AVAILABILITY
        if (slot.getRemainingSlots() <= 0) {

            throw new RuntimeException(
                    "No slots left for this date"
            );
        }

        // ✅ REDUCE SLOT
        slot.setRemainingSlots(
                slot.getRemainingSlots() - 1
        );

        slotRepo.save(slot);

        // ✅ SAVE BOOKING
        return bookingRepo.save(booking);
    }

    // ✅ USER BOOKINGS
    public List<Booking> getUserBookings(Long userId) {

        List<Booking> list =
                bookingRepo.findByUserId(userId);

        for (Booking b : list) {

            if (
                    b.getDate() != null &&
                    b.getDate().isBefore(LocalDate.now())
            ) {

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

        return bookingRepo.countByDate(
                LocalDate.now()
        );
    }

    // ✅ TODAY INCOME
    public double getTodayIncome() {

        List<Booking> todayBookings =
                bookingRepo.findByDate(LocalDate.now());

        double total = 0;

        for (Booking b : todayBookings) {

            total += getOfferAmount(
                    b.getOfferId()
            );
        }

        return total;
    }

    // ✅ YEAR INCOME
    public double getYearIncome() {

        int currentYear =
                LocalDate.now().getYear();

        List<Booking> all =
                bookingRepo.findAll();

        double total = 0;

        for (Booking b : all) {

            if (
                    "COMPLETED".equals(b.getStatus()) &&
                    b.getDate() != null &&
                    b.getDate().getYear() == currentYear
            ) {

                total += getOfferAmount(
                        b.getOfferId()
                );
            }
        }

        return total;
    }

    // ✅ HELPER
    private double getOfferAmount(Long offerId) {

        return offerRepo.findById(offerId)
                .map(o -> o.getAmount())
                .orElse(0.0);
    }

    // ✅ TOTAL USERS
    public long getTotalUsers() {

        return loginRepo.count();
    }
}