package com.college.smartparking.repository;

import com.college.smartparking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdOrderByBookingDateDesc(Long userId);

    List<Booking> findBySpotOwnerIdOrderByBookingDateDesc(Long ownerId);

    // This is the core logic highlight of the project: Booking Conflict Detection!
    // It checks if there is any ACTIVE booking for the same spot and date,
    // where the time overlaps with the requested start and end time.
    @Query("SELECT b FROM Booking b WHERE b.spot.id = :spotId " +
            "AND b.bookingDate = :date " +
            "AND b.status = 'ACTIVE' " +
            "AND (b.startTime < :endTime AND b.endTime > :startTime)")
    List<Booking> findConflictingBookings(@Param("spotId") Long spotId,
                                          @Param("date") LocalDate date,
                                          @Param("startTime") LocalTime startTime,
                                          @Param("endTime") LocalTime endTime);
}