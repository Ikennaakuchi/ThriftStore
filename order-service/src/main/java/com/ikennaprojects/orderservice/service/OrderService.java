package com.ikennaprojects.orderservice.service;

import com.ikennaprojects.orderservice.dto.OrderRequest;

public interface OrderService {
    void placeOrder(OrderRequest orderRequest);
}
