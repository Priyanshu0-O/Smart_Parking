package com.college.smartparking.controller;

import com.college.smartparking.dto.ParkingSpotDto;
import com.college.smartparking.service.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/spots")
public class ParkingSpotController {

    @Autowired
    private ParkingSpotService spotService;

    @PostMapping
    public ResponseEntity<ParkingSpotDto> addSpot(@Valid @RequestBody ParkingSpotDto dto) {
        return new ResponseEntity<>(spotService.addParkingSpot(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpotDto> updateSpot(@PathVariable Long id, @Valid @RequestBody ParkingSpotDto dto) {
        return new ResponseEntity<>(spotService.updateParkingSpot(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpot(@PathVariable Long id) {
        spotService.deleteParkingSpot(id);
        return new ResponseEntity<>(Map.of("message", "Spot deleted successfully"), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotDto>> getAllAvailableSpots() {
        return new ResponseEntity<>(spotService.getAllAvailableSpots(), HttpStatus.OK);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingSpotDto>> getSpotsByOwner(@PathVariable Long ownerId) {
        return new ResponseEntity<>(spotService.getSpotsByOwner(ownerId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ParkingSpotDto>> searchSpots(@RequestParam String keyword) {
        return new ResponseEntity<>(spotService.searchSpotsByAddress(keyword), HttpStatus.OK);
    }
}