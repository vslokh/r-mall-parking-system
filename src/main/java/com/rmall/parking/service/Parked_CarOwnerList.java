package com.rmall.parking.service;

import com.rmall.parking.model.Parked_CarOwner_Details;
import com.rmall.parking.model.ParkingSlot;
import com.rmall.parking.exception.ParkingFullException;
import com.rmall.parking.util.InputValidator;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class Parked_CarOwnerList {
    private static final int TOTAL_FLOORS = 3;
    private static final int SECTIONS_PER_FLOOR = 4;
    private static final int SLOTS_PER_SECTION = 20;
    private static final char[] SECTIONS = {'A', 'B', 'C', 'D'};

    private final ParkingSlot[][][] parkingLot;
    private final ConcurrentHashMap<Integer, ParkingSlot> tokenMap;
    private final ConcurrentHashMap<String, Integer> carNumberMap;

    public Parked_CarOwnerList() {
        parkingLot = new ParkingSlot[TOTAL_FLOORS][SECTIONS_PER_FLOOR][SLOTS_PER_SECTION];
        tokenMap = new ConcurrentHashMap<>();
        carNumberMap = new ConcurrentHashMap<>();
        for (var floor = 0; floor < TOTAL_FLOORS; floor++) {
            for (var section = 0; section < SECTIONS_PER_FLOOR; section++) {
                for (var position = 0; position < SLOTS_PER_SECTION; position++) {
                    parkingLot[floor][section][position] = new ParkingSlot(floor, SECTIONS[section], position + 1);
                }
            }
        }
    }

    public int add_new_car(Parked_CarOwner_Details carOwner) throws ParkingFullException {
        if (!InputValidator.isValidCarNumber(carOwner.carNo())) {
            throw new IllegalArgumentException("Invalid car number format");
        }
        var carNo = carOwner.carNo().toUpperCase();
        if (carNumberMap.containsKey(carNo)) {
            System.out.println("Car already parked!");
            return -1;
        }
        var availableSlot = getAllSlotsStream().filter(slot -> !slot.isOccupied()).findFirst();
        if (availableSlot.isEmpty()) {
            throw new ParkingFullException("Parking is full!");
        }
        var slot = availableSlot.get();
        slot.parkCar(carOwner);
        var token = generateToken(slot.floor(), getSectionIndex(slot.section()), slot.position());
        tokenMap.put(token, slot);
        carNumberMap.put(carNo, token);
        return token;
    }

    public boolean remove_car(int token) {
        var slot = tokenMap.get(token);
        if (slot == null) {
            System.out.println("Invalid token!");
            return false;
        }
        slot.getParkedCar().ifPresent(car -> {
            var carNo = car.carNo().toUpperCase();
            carNumberMap.remove(carNo);
        });
        slot.clearSlot();
        tokenMap.remove(token);
        return true;
    }

    public String get_parked_car_location(int token) {
        var slot = tokenMap.get(token);
        if (slot == null) {
            return "Invalid token!";
        }
        return "Floor: " + slot.floor() + ", Section: " + slot.section() + ", Position: " + slot.position();
    }

    public Optional<Parked_CarOwner_Details> get_car_details(int token) {
        var slot = tokenMap.get(token);
        return slot != null ? slot.getParkedCar() : Optional.empty();
    }

    public void display_parking_status() {
        var totalCapacity = TOTAL_FLOORS * SECTIONS_PER_FLOOR * SLOTS_PER_SECTION;
        var totalOccupied = (int) getAllSlotsStream().filter(ParkingSlot::isOccupied).count();
        System.out.println("=== R-MALL PARKING STATUS ===");
        System.out.println("Total: " + totalOccupied + "/" + totalCapacity + " occupied");
        System.out.println("Available: " + (totalCapacity - totalOccupied) + " slots");
        for (var floor = 0; floor < TOTAL_FLOORS; floor++) {
            var floorOccupied = countOccupiedInFloor(floor);
            System.out.println("Floor " + floor + ": " + floorOccupied + "/80 occupied");
        }
    }

    public boolean is_parking_full() {
        return getAllSlotsStream().allMatch(ParkingSlot::isOccupied);
    }

    public int get_available_slots() {
        return (int) getAllSlotsStream().filter(slot -> !slot.isOccupied()).count();
    }

    public Optional<Integer> search_by_car_number(String carNo) {
        return Optional.ofNullable(carNumberMap.get(carNo.toUpperCase()));
    }

    private int generateToken(int floor, int section, int position) {
        return (floor * 10000) + (section * 1000) + position;
    }

    private int getSectionIndex(char section) {
        return switch (section) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            default -> throw new IllegalArgumentException("Invalid section");
        };
    }

    private Stream<ParkingSlot> getAllSlotsStream() {
        return Arrays.stream(parkingLot).flatMap(Arrays::stream).flatMap(Arrays::stream);
    }

    private long countOccupiedInFloor(int floor) {
        return Arrays.stream(parkingLot[floor]).flatMap(Arrays::stream).filter(ParkingSlot::isOccupied).count();
    }
}