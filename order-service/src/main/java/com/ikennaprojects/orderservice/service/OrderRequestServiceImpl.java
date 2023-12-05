package com.ikennaprojects.orderservice.service;

import com.ikennaprojects.orderservice.dto.InventoryResponse;
import com.ikennaprojects.orderservice.dto.OrderLineItemsDto;
import com.ikennaprojects.orderservice.dto.OrderRequest;
import com.ikennaprojects.orderservice.exception.OutOfStockException;
import com.ikennaprojects.orderservice.model.Order;
import com.ikennaprojects.orderservice.model.OrderLineItems;
import com.ikennaprojects.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderRequestServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode).toList();

        // Call Inventory Service and place order if order is in stock
        InventoryResponse[] inventoryResponseArr = webClientBuilder.build().get()
                .uri("http://inventory-service:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                        .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                        .block();

        boolean allProductsInStock = Arrays.stream(inventoryResponseArr)
                .allMatch(InventoryResponse::getIsInStock);

        if(allProductsInStock){
            log.info("The items are currently in stock");
            orderRepository.save(order);
        }else {
            log.info("The items are currently out of stock");
            throw new OutOfStockException("The Items are currently not available in stock");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

        return orderLineItems;
    }
}
