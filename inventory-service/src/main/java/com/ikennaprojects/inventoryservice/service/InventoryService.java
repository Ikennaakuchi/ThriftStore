package com.ikennaprojects.inventoryservice.service;

import com.ikennaprojects.inventoryservice.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
