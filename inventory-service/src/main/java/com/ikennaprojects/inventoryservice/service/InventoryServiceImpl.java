package com.ikennaprojects.inventoryservice.service;

import com.ikennaprojects.inventoryservice.dto.InventoryResponse;
import com.ikennaprojects.inventoryservice.model.Inventory;
import com.ikennaprojects.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    @Override
    @Transactional(readOnly = true)
    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCodes) {
        log.info("Waiting");
        Thread.sleep(10000);
        log.info("Wait Ended");
        List<Inventory> inventoryList = inventoryRepository.findBySkuCodeIn(skuCodes);

        return inventoryList.stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build()
                ).toList();
    }
}
