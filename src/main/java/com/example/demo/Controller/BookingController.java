package com.example.demo.Controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entity.Booking;
import com.example.demo.Repository.BookingRepository;
import com.example.demo.Service.BookingService;

@RestController
@RequestMapping("/bookings")
@CrossOrigin("*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
private BookingRepository bookingRepository;

    // ✅ BOOK SLOT
    @PostMapping("/book")
   
public Booking bookSlot(
        @RequestParam Long userId,
        @RequestParam String email,
        @RequestParam Long offerId,
        @RequestParam String shootName,
        @RequestParam String date,
        @RequestParam String startTime,
        @RequestParam String duration
) {

    return bookingService.bookSlot(
            userId,
            email,
            offerId,
            shootName,
            date,
            startTime,
            duration
    );
}

    // ✅ USER BOOKINGS
    @GetMapping("/user")
    public List<Booking> getUserBookings(@RequestParam Long userId) {
        return bookingService.getUserBookings(userId);
    }

    // ✅ ADMIN - ALL BOOKINGS
    @GetMapping("/admin")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }
    @PutMapping("/update-status/{id}")

public ResponseEntity<?> updateBookingStatus(

@PathVariable Long id,

@RequestParam Double amountPaid,

@RequestParam String status,

@RequestParam(required = false) String driveLink,

@RequestParam(required = false) String adminMessage

){

    Booking booking =
    bookingRepository.findById(id).orElseThrow();

    booking.setAmountPaid(amountPaid);

    booking.setStatus(status);

    booking.setDriveLink(driveLink);

    booking.setAdminMessage(adminMessage);

    bookingRepository.save(booking);

    return ResponseEntity.ok("Booking Updated");

}

    // ✅ ADMIN DASHBOARD
    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {

        Map<String, Object> map = new HashMap<>();

        map.put("todayOrders", bookingService.getTodayOrders());
        map.put("todayIncome", bookingService.getTodayIncome());
        map.put("yearIncome", bookingService.getYearIncome());
       map.put("totalUsers", bookingService.getTotalUsers());

        return map;
    }
}