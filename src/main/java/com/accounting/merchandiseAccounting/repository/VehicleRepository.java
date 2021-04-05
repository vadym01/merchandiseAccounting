package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository {
//    Vehicle saveVehicle(Vehicle vehicle);
//    Vehicle findVehicleById(long id);
//    int deleteVehicleById(long id);
//    List<Vehicle> getAllVehicles();
    List<Vehicle> getAllAvailableVehicles();
    List<Vehicle> findVehiclesByVehicleName(String VehicleName);
//    void updateVehicle(Vehicle vehicle);
}
