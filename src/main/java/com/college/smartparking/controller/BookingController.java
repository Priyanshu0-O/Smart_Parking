package com.college.smartparking.controller;

import com.college.smartparking.dto.BookingDto;
import com.college.smartparking.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto dto) {
        return new ResponseEntity<>(bookingService.createBooking(dto), HttpStatus.CREATED);
    }

    @GetMapping("/customer/{userId}")
    public ResponseEntity<List<BookingDto>> getCustomerBookings(@PathVariable Long userId) {
        return new ResponseEntity<>(bookingService.getBookingsByCustomer(userId), HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<BookingDto>> getOwnerBookings(@PathVariable Long ownerId) {
        return new ResponseEntity<>(bookingService.getBookingsByOwner(ownerId), HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<BookingDto> cancelBooking(@PathVariable Long id) {
        return new ResponseEntity<>(bookingService.cancelBooking(id), HttpStatus.OK);
    }
}