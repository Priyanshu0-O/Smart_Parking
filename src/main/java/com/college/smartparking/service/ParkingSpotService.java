package com.college.smartparking.service;

import com.college.smartparking.dto.ParkingSpotDto;
import com.college.smartparking.entity.ParkingSpot;
import com.college.smartparking.entity.User;
import com.college.smartparking.exception.ResourceNotFoundException;
import com.college.smartparking.repository.ParkingSpotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingSpotService {

    @Autowired
    private ParkingSpotRepository spotRepository;

    @Autowired
    private UserService userService;

    public ParkingSpotDto addParkingSpot(ParkingSpotDto dto) {
        User owner = userService.getUserById(dto.getOwnerId());
        if (!owner.getRole().equals("OWNER")) {
            throw new RuntimeException("Only owners can add parking spots!");
        }

        ParkingSpot spot = new ParkingSpot();
        spot.setOwner(owner);
        spot.setTitle(dto.getTitle());
        spot.setAddress(dto.getAddress());
        spot.setVehicleType(dto.getVehicleType().toUpperCase());
        spot.setPricePerHour(dto.getPricePerHour());
        spot.setAvailable(true);

        ParkingSpot savedSpot = spotRepository.save(spot);
        return convertToDto(savedSpot);
    }

    public ParkingSpotDto updateParkingSpot(Long id, ParkingSpotDto dto) {
        ParkingSpot spot = spotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Spot not found"));

        spot.setTitle(dto.getTitle());
        spot.setAddress(dto.getAddress());
        spot.setVehicleType(dto.getVehicleType().toUpperCase());
        spot.setPricePerHour(dto.getPricePerHour());
        if(dto.getAvailable() != null) {
            spot.setAvailable(dto.getAvailable());
        }

        return convertToDto(spotRepository.save(spot));
    }

    public void deleteParkingSpot(Long id) {
        if (!spotRepository.existsById(id)) {
            throw new ResourceNotFoundException("Spot not found");
        }
        spotRepository.deleteById(id);
    }

    public List<ParkingSpotDto> getAllAvailableSpots() {
        return spotRepository.findByAvailableTrue().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ParkingSpotDto> getSpotsByOwner(Long ownerId) {
        return spotRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<ParkingSpotDto> searchSpotsByAddress(String keyword) {
        return spotRepository.findByAvailableTrueAndAddressContainingIgnoreCase(keyword).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ParkingSpot getSpotEntityById(Long id) {
        return spotRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Parking Spot not found with id: " + id));
    }

    private ParkingSpotDto convertToDto(ParkingSpot spot) {
        ParkingSpotDto dto = new ParkingSpotDto();
        dto.setId(spot.getId());
        dto.setOwnerId(spot.getOwner().getId());
        dto.setOwnerName(spot.getOwner().getName());
        dto.setTitle(spot.getTitle());
        dto.setAddress(spot.getAddress());
        dto.setVehicleType(spot.getVehicleType());
        dto.setPricePerHour(spot.getPricePerHour());
        dto.setAvailable(spot.getAvailable());
        return dto;
    }
}