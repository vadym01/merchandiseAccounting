package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Vehicle;

import java.util.List;

public interface VehicleService {
    void saveVehicle(Vehicle vehicle);
    Vehicle findVehicleById(long id);
    int deleteVehicleById(long id);
    List<Vehicle> getAllVehicle();
    void updateAvailableStatusById (long id);
    List<Vehicle> getAllAvailableVehicle();
    List<Vehicle> findVehicleByVehicleName(String vehicleName);
    void updateVehicle(Vehicle vehicle);
}
