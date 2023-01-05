package com.billing.dto;

import com.billing.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 6578026379385085864L;
    private Long productId;
    private String name;
    private BigDecimal price;

    public static ProductDto of(Product product) {
        return ProductDto.builder()
                .productId(product.getId())
                .name(product.getName())
                .price(product.getAmount())
                .build();
    }
}
