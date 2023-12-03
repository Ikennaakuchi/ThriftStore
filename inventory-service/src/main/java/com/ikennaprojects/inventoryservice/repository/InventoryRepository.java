package com.ikennaprojects.inventoryservice.repository;

import com.ikennaprojects.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Boolean existsBySkuCode(String skuCode);
}
