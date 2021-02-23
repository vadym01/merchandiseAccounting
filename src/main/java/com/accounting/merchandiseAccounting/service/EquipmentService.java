package com.accounting.merchandiseAccounting.service;

import com.accounting.merchandiseAccounting.model.Equipment;

import java.util.List;

public interface EquipmentService {
    void saveEquipment(Equipment equipment);
    Equipment findEquipmentById(long id);
    int deleteEquipmentById(long id);
    List<Equipment> getAllEquipment();
}
