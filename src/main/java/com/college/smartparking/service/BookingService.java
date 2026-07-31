package com.college.smartparking.service;

import com.college.smartparking.dto.BookingDto;
import com.college.smartparking.entity.Booking;
import com.college.smartparking.entity.ParkingSpot;
import com.college.smartparking.entity.User;
import com.college.smartparking.exception.BookingConflictException;
import com.college.smartparking.exception.ResourceNotFoundException;
import com.college.smartparking.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ParkingSpotService spotService;

    // --- HIGHLIGHT: Conflict Detection Logic ---
    public BookingDto createBooking(BookingDto dto) {
        User customer = userService.getUserById(dto.getUserId());
        ParkingSpot spot = spotService.getSpotEntityById(dto.getSpotId());

        if (!spot.getAvailable()) {
            throw new RuntimeException("This parking spot is currently disabled by the owner.");
        }

        if (dto.getStartTime().isAfter(dto.getEndTime()) || dto.getStartTime().equals(dto.getEndTime())) {
            throw new RuntimeException("End time must be after start time!");
        }

        // Check for conflicts in the database
        List<Booking> conflicts = bookingRepository.findConflictingBookings(
                spot.getId(), dto.getBookingDate(), dto.getStartTime(), dto.getEndTime()
        );

        if (!conflicts.isEmpty()) {
            throw new BookingConflictException("The selected time slot is already booked for this spot! Please choose a different time.");
        }

        // Calculate Total Amount
        long hours = Duration.between(dto.getStartTime(), dto.getEndTime()).toHours();
        if (hours == 0) hours = 1; // minimum 1 hour charge
        double totalAmount = hours * spot.getPricePerHour();

        Booking booking = new Booking();
        booking.setUser(customer);
        booking.setSpot(spot);
        booking.setBookingDate(dto.getBookingDate());
        booking.setStartTime(dto.getStartTime());
        booking.setEndTime(dto.getEndTime());
        booking.setStatus("ACTIVE");
        booking.setTotalAmount(totalAmount);

        Booking savedBooking = bookingRepository.save(booking);
        return convertToDto(savedBooking);
    }

    public List<BookingDto> getBookingsByCustomer(Long userId) {
        return bookingRepository.findByUserIdOrderByBookingDateDesc(userId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<BookingDto> getBookingsByOwner(Long ownerId) {
        return bookingRepository.findBySpotOwnerIdOrderByBookingDateDesc(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public BookingDto cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        booking.setStatus("CANCELLED");
        return convertToDto(bookingRepository.save(booking));
    }

    private BookingDto convertToDto(Booking booking) {
        BookingDto dto = new BookingDto();
        dto.setId(booking.getId());
        dto.setUserId(booking.getUser().getId());
        dto.setUserName(booking.getUser().getName());
        dto.setSpotId(booking.getSpot().getId());
        dto.setSpotTitle(booking.getSpot().getTitle());
        dto.setBookingDate(booking.getBookingDate());
        dto.setStartTime(booking.getStartTime());
        dto.setEndTime(booking.getEndTime());
        dto.setStatus(booking.getStatus());
        dto.setTotalAmount(booking.getTotalAmount());
        return dto;
    }
}