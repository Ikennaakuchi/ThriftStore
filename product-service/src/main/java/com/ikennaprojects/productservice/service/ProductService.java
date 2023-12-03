package com.ikennaprojects.productservice.service;

import com.ikennaprojects.productservice.dto.ProductRequest;
import com.ikennaprojects.productservice.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    void createProduct(ProductRequest productRequest);
    List<ProductResponse> getAllProduct();
}
