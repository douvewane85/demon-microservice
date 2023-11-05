package com.wane.inventoryservice.service;

import com.wane.inventoryservice.data.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
