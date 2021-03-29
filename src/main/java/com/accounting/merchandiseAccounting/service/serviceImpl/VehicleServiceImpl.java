package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Employee;
import com.accounting.merchandiseAccounting.model.Vehicle;
import com.accounting.merchandiseAccounting.repository.VehicleRepository;
import com.accounting.merchandiseAccounting.service.VehicleService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Override
    public Vehicle saveVehicle(Vehicle vehicle) {
        Vehicle newVehicle = vehicleRepository.saveVehicle(vehicle);
        return newVehicle;
    }

    @Override
    public Vehicle findVehicleById(long id) {
        return vehicleRepository.findVehicleById(id);
    }

    @Override
    public int deleteVehicleById(long id) {
        return vehicleRepository.deleteVehicleById(id);
    }

    @Override
    public List<Vehicle> getAllVehicle() {
        return vehicleRepository.getAllVehicle();
    }

    @Override
    public List<Vehicle> getAllAvailableVehicle() {
        List<Vehicle> vehicleList = vehicleRepository.getAllAvailableVehicle();
        return vehicleList;
    }

    @Override
    public List<Vehicle> findVehicleByVehicleName(String vehicleName) {
        List<Vehicle> vehicleList = vehicleRepository.findVehicleByVehicleName(vehicleName);
        return vehicleList;
    }

    @Override
    public void updateVehicle(Vehicle vehicle) {
        vehicleRepository.updateVehicle(vehicle);
    }


    @Override
    public void changeAvailableStatusForVehicle(long vehicleId) {
        Vehicle vehicle = findVehicleById(vehicleId);
        vehicle.setAvailable(!vehicle.isAvailable());
        updateVehicle(vehicle);
    }
}
