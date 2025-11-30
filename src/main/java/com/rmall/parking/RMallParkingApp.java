package com.rmall.parking;

import com.rmall.parking.model.Parked_CarOwner_Details;
import com.rmall.parking.service.Parked_CarOwnerList;
import com.rmall.parking.exception.ParkingFullException;
import java.util.Scanner;

public class RMallParkingApp {
    private static final Parked_CarOwnerList parkingService = new Parked_CarOwnerList();
    private static final Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("   🅿️  WELCOME TO R-MALL PARKING MANAGEMENT SYSTEM");
        System.out.println("=".repeat(60) + "\n");
        
        while (true) {
            displayMenu();
            var choice = getIntInput("Enter your choice: ");
            
            try {
                var result = switch (choice) {
                    case 1 -> { parkNewCar(); yield true; }
                    case 2 -> { removeCar(); yield true; }
                    case 3 -> { getCarLocation(); yield true; }
                    case 4 -> { getCarDetails(); yield true; }
                    case 5 -> { displayStatus(); yield true; }
                    case 6 -> { searchByCarNumber(); yield true; }
                    case 7 -> { System.out.println("\n👋 Thank you!"); yield false; }
                    default -> { System.out.println("❌ Invalid choice!"); yield true; }
                };
                if (!result) break;
            } catch (Exception e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
        }
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("\n" + "─".repeat(60));
        System.out.println("                    MAIN MENU");
        System.out.println("─".repeat(60));
        System.out.println("1. 🚗 Park New Car");
        System.out.println("2. 🚙 Remove Car");
        System.out.println("3. 📍 Get Car Location");
        System.out.println("4. ℹ️  Get Car Details");
        System.out.println("5. 📊 Display Parking Status");
        System.out.println("6. 🔍 Search by Car Number");
        System.out.println("7. 🚪 Exit");
        System.out.println("─".repeat(60));
    }
    
    private static void parkNewCar() throws ParkingFullException {
        System.out.println("\n=== PARK NEW CAR ===");
        var ownerName = getStringInput("Enter owner name: ");
        var carModel = getStringInput("Enter car model: ");
        var carNo = getStringInput("Enter car number: ");
        var mobile = getStringInput("Enter mobile (10 digits): ");
        var address = getStringInput("Enter address: ");
        var carOwner = new Parked_CarOwner_Details(ownerName, carModel, carNo, mobile, address);
        var token = parkingService.add_new_car(carOwner);
        if (token > 0) {
            System.out.println("\n✅ Car parked successfully!");
            System.out.println("🎫 Your token: " + token);
        }
    }
    
    private static void removeCar() {
        System.out.println("\n=== REMOVE CAR ===");
        var token = getIntInput("Enter token: ");
        if (parkingService.remove_car(token)) {
            System.out.println("✅ Car removed!");
        }
    }
    
    private static void getCarLocation() {
        var token = getIntInput("Enter token: ");
        System.out.println(parkingService.get_parked_car_location(token));
    }
    
    private static void getCarDetails() {
        var token = getIntInput("Enter token: ");
        parkingService.get_car_details(token).ifPresentOrElse(
            car -> System.out.println(car),
            () -> System.out.println("❌ Not found")
        );
    }
    
    private static void displayStatus() {
        parkingService.display_parking_status();
    }
    
    private static void searchByCarNumber() {
        var carNo = getStringInput("Enter car number: ");
        parkingService.search_by_car_number(carNo).ifPresentOrElse(
            token -> System.out.println("✅ Found! Token: " + token),
            () -> System.out.println("❌ Not found")
        );
    }
    
    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}