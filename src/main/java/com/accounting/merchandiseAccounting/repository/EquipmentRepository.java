package com.accounting.merchandiseAccounting.repository;

import com.accounting.merchandiseAccounting.model.Equipment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepository{
    void saveEquipment(Equipment equipment);
    Equipment findEquipmentById(long id);
    int deleteEquipmentById(long id);
    List<Equipment> getAllEquipment();
    void updateAvailableStatusById(long id);
    List<Equipment> getAllAvailableEquipment();
}
