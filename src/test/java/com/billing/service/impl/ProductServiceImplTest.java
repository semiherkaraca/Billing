package com.billing.service.impl;

import com.billing.dto.ProductDto;
import com.billing.exception.BadRequestException;
import com.billing.model.Product;
import com.billing.model.User;
import com.billing.repository.ProductRepository;
import com.billing.request.ProductRequest;
import com.billing.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @Test
    public void shouldReturnProductListByEmail() {
        //given
        String dummyEmail = "dummy@gmail.com";
        Product product1 = Product.builder().id(1L).name("Product1").build();
        Product product2 = Product.builder().id(2L).name("Product2").build();
        Product product3 = Product.builder().id(3L).name("Product3").build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1, product2, product3))
                .email(dummyEmail).build();

        //when
        when(userService.findByEmail(dummyEmail)).thenReturn(user);
        //then
        List<ProductDto> userProducts = productService.getUserProducts(dummyEmail);

        assertEquals(product1.getId(), userProducts.get(0).getProductId());
    }

    @Test
    public void shouldReturnProductDtoWhenSaveNewProduct() {
        //given
        String dummyEmail = "dummy@gmail.com";
        Product product1 = Product.builder().id(1L).name("Product1").build();
        Product product2 = Product.builder().id(2L).name("Product2").build();
        Product product3 = Product.builder().id(3L).name("Product3").build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1, product2))
                .email(dummyEmail).build();
        ProductRequest productRequest = new ProductRequest(dummyEmail, "Product1", new BigDecimal(10));
        //when
        when(userService.findByEmail(dummyEmail)).thenReturn(user);
        when(productRepository.findByUserAndName(user, productRequest.getName())).thenReturn(Optional.empty());
        Product product = Product.generateProduct(productRequest, user);
        when(productRepository.save(product)).thenReturn(product3);

        //then
        ProductDto productDto = productService.saveProduct(productRequest);

        assertEquals(product3.getName(), productDto.getName());
    }

    @Test
    public void shouldThrowBadRequestExceptionWhenProductExistCurrentUser() {
        //given
        String dummyEmail = "dummy@gmail.com";
        Product product1 = Product.builder().id(1L).name("Product1").build();
        Product product2 = Product.builder().id(2L).name("Product2").build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1, product2))
                .email(dummyEmail).build();
        ProductRequest productRequest = new ProductRequest(dummyEmail, "Product1", new BigDecimal(10));
        //when
        when(userService.findByEmail(dummyEmail)).thenReturn(user);
        when(productRepository.findByUserAndName(user, productRequest.getName())).thenReturn(Optional.of(product1));
        try {
            ProductDto productDto = productService.saveProduct(productRequest);
        } catch (Exception e) {
            //then
            assertTrue(e instanceof BadRequestException);
            assertEquals("This product already created before", e.getMessage());
        }
    }

    @Test
    public void shouldReturnProductWhenProductExistsByUserAndName() {
        //given
        String dummyEmail = "dummy@gmail.com";
        Product product1 = Product.builder().id(2L).name("Product2").build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1))
                .email(dummyEmail).build();
        //when
        when(productRepository.findByUserAndName(user, product1.getName())).thenReturn(Optional.of(product1));
        //then
        Product productByName = productService.findProductByUserAndName(user, product1.getName());
        assertEquals(product1.getName(), productByName.getName());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenProductNotExists() {
        //given
        String dummyEmail = "dummy@gmail.com";
        Product product1 = Product.builder().id(2L).name("Product2").build();
        User user = User.builder().firstname("Semih").lastname("Erkaraca")
                .products(Arrays.asList(product1))
                .email(dummyEmail).build();
        //when
        when(productRepository.findByUserAndName(any(), any())).thenReturn(Optional.empty());
        //then
        try {
            productService.findProductByUserAndName(user, product1.getName());
        } catch (Exception e) {
            //then
            assertTrue(e instanceof BadRequestException);
            String message = "Cannot find product";
            assertEquals(message, e.getMessage());
        }
    }
}