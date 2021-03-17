package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Vehicle;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository {
    void saveVehicle(Vehicle vehicle);
    Vehicle findVehicleById(long id);
    int deleteVehicleById(long id);
    List<Vehicle> getAllVehicle();
    List<Vehicle> getAllAvailableVehicle();
    List<Vehicle> findVehicleByVehicleName(String VehicleName);
    void updateVehicle(Vehicle vehicle);
}
