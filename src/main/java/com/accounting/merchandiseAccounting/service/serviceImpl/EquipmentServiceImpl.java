package com.accounting.merchandiseAccounting.service.serviceImpl;

import com.accounting.merchandiseAccounting.model.Equipment;
import com.accounting.merchandiseAccounting.repository.EquipmentRepository;
import com.accounting.merchandiseAccounting.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Override
    public void saveEquipment(Equipment equipment) {
        equipmentRepository.saveEquipment(equipment);
    }

    @Override
    public Equipment findEquipmentById(long id) {
        return equipmentRepository.findEquipmentById(id);
    }

    @Override
    public void updateAvailableStatusById(long id) {
        equipmentRepository.updateAvailableStatusById(id);
    }

    @Override
    public int deleteEquipmentById(long id) {
        return equipmentRepository.deleteEquipmentById(id);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.getAllEquipment();
    }

    @Override
    public List<Equipment> getAllAvailableEquipment() {
        List<Equipment> equipmentList = equipmentRepository.getAllAvailableEquipment();
        return equipmentList;
    }
}
