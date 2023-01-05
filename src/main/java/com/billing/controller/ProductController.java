package com.billing.controller;

import com.billing.constants.RequestMapConstants;
import com.billing.dto.ProductDto;
import com.billing.request.ProductRequest;
import com.billing.service.ProductService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(RequestMapConstants.PRODUCT_PATH)
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    @ApiOperation(value = "Get Product By Id")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok().body(productService.getProductById(productId));
    }

    @GetMapping("/user/{useremail}")
    @ApiOperation(value = "Get User's Product By User email")
    public ResponseEntity<List<ProductDto>> getUserProducts(@PathVariable("useremail") String useremail) {
        return ResponseEntity.ok().body(productService.getUserProducts(useremail));
    }

    @PostMapping
    @ApiOperation(value = "Save New product")
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductRequest productRequest) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(RequestMapConstants.PRODUCT_PATH + "/save").toUriString());
        return ResponseEntity.created(uri).body(productService.saveProduct(productRequest));
    }
}
