package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import com.accounting.merchandiseAccounting.repository.crudRepository.CrudProvider;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    private CrudProvider<Vehicle> crudProvider;

    @Autowired
    public void setCrudProvider(CrudProvider<Vehicle> crudProvider) {
        this.crudProvider = crudProvider;
        this.crudProvider.setClassInstance(Vehicle.class);
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        Vehicle newVehicle = crudProvider.save(vehicle);
        return newVehicle;
    }

    @Override
    public Vehicle findVehicleById(long id) {
        return crudProvider.findOneById(id);
    }

    @Override
    public void deleteVehicleById(long id) {
        crudProvider.deleteById(id);
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.addAll(crudProvider.findAll());
        return vehicleList;
    }

    @Override
    public List<Vehicle> getAllAvailableVehicles() {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.addAll(vehicleRepository.getAllAvailableVehicles());
        return vehicleList;
    }

    @Override
    public List<Vehicle> findVehicleByVehicleName(String vehicleName) {
        List<Vehicle> vehicleList = new ArrayList<>();
        vehicleList.addAll(vehicleRepository.findVehiclesByVehicleName(vehicleName));
        return vehicleList;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        crudProvider.update(vehicle);
    }


    @Override
    public void changeAvailableStatusForVehicle(long vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        vehicle.setAvailable(!vehicle.isAvailable());
        updateVehicle(vehicle);
    }
}
