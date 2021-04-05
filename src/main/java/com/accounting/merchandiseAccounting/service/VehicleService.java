package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Vehicle;

import java.util.List;

public interface VehicleService {
    Vehicle saveVehicle(Vehicle vehicle);
    Vehicle findVehicleById(long id);
    void deleteVehicleById(long id);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getAllAvailableVehicles();
    List<Vehicle> findVehicleByVehicleName(String vehicleName);
    void updateVehicle(Vehicle vehicle);
    void changeAvailableStatusForVehicle(long id);
}

