package com.billing.service;

import com.billing.dto.ProductDto;
import com.billing.model.Product;
import com.billing.model.User;
import com.billing.request.ProductRequest;

import java.util.List;

public interface ProductService {

    ProductDto getProductById(Long productId);

    List<ProductDto> getUserProducts(String email);

    ProductDto saveProduct(ProductRequest productRequest);

    Product findProductByUserAndName(User user, String name);

    List<Long> getProductIds(Long userId);
}
