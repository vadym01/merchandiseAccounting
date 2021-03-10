package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Equipment;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(Equipment equipment);
    Equipment findEquipmentById(long id);
    int deleteEquipmentById(long id);
    List<Equipment> getAllEquipment();
    void updateAvailableStatusById (long id);
    List<Equipment> getAllAvailableEquipment();
    List<Equipment> findEquipmentByEquipmentName(String equipmentName);
    void updateEquipment(Equipment equipment);
}
