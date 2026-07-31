package com.college.smartparking.repository;

import com.college.smartparking.entity.ParkingSpot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ParkingSpotRepository extends JpaRepository<ParkingSpot, Long> {
    List<ParkingSpot> findByOwnerId(Long ownerId);
    List<ParkingSpot> findByAvailableTrue();
    List<ParkingSpot> findByAvailableTrueAndAddressContainingIgnoreCase(String address);
}