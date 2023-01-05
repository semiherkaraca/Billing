package com.billing.service.impl;

import com.billing.dto.ProductDto;
import com.billing.exception.BadRequestException;
import com.billing.exception.NotFoundException;
import com.billing.model.Product;
import com.billing.model.User;
import com.billing.repository.ProductRepository;
import com.billing.request.ProductRequest;
import com.billing.service.ProductService;
import com.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    @Override
    public List<ProductDto> getUserProducts(String email) {
        User user = userService.findByEmail(email);
        return user.getProducts().stream()
                .map(ProductDto::of)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductDto saveProduct(ProductRequest productRequest) {
        User user = userService.findByEmail(productRequest.getUserEmail());
        Optional<Product> optionalProduct = productRepository.findByUserAndName(user, productRequest.getName());
        if (optionalProduct.isPresent()) {
            throw new BadRequestException("This product already created before");
        }
        Product product = productRepository.save(Product.generateProduct(productRequest, user));
        return ProductDto.of(product);
    }

    @Override
    public Product findProductByUserAndName(User user, String name) {
        Optional<Product> optionalProduct = productRepository.findByUserAndName(user, name);
        if (optionalProduct.isEmpty()) {
            throw new BadRequestException("Cannot find product");
        }
        return optionalProduct.get();
    }

    @Override
    public List<Long> getProductIds(Long userId) {
        return productRepository.getProductIdListByuser(userId);
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new NotFoundException("Product with Id: " + productId + " is not found");
        }
        return ProductDto.of(optionalProduct.get());
    }
}
