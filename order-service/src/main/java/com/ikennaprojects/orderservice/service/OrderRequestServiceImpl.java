package com.ikennaprojects.orderservice.service;

import com.ikennaprojects.orderservice.dto.OrderLineItemsDto;
import com.ikennaprojects.orderservice.dto.OrderRequest;
import com.ikennaprojects.orderservice.exception.OutOfStockException;
import com.ikennaprojects.orderservice.model.Order;
import com.ikennaprojects.orderservice.model.OrderLineItems;
import com.ikennaprojects.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderRequestServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final WebClient webClient;

    @Override
    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList().stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        // Call Inventory Service and place order if order is in stock
        Boolean isInStock = webClient.get()
                .uri("http://localhost:8082/api/inventory")
                        .retrieve()
                        .bodyToMono(Boolean.class)
                        .block();

        if(Boolean.TRUE.equals(isInStock)){
            orderRepository.save(order);
        }else {
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
