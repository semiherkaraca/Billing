package com.billing;

import com.billing.dto.ProductDto;
import com.billing.dto.UserDto;
import com.billing.model.User;
import com.billing.request.InvoiceRequest;
import com.billing.request.MultipleInvoiceRequest;
import com.billing.request.ProductRequest;
import com.billing.service.InvoiceService;
import com.billing.service.ProductService;
import com.billing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DatabaseLoader { /*implements CommandLineRunner
    private final UserService userService;
    private final ProductService productService;
    private final InvoiceService invoiceService;

    @Override
    public void run(String... args) throws Exception {
       User user1 = userService.saveUser(UserDto.builder().name("Semih").lastname("Erkaraca").email("semiherkaraca@windowslive.com").build());
        User user2 = userService.saveUser(UserDto.builder().name("Ali").lastname("Veli").email("aliveli@gmail.com").build());
        User user3 = userService.saveUser(UserDto.builder().name("Kemal").lastname("Kırmızı").email("kemalkirmizi@gmail.com").build());

        ProductDto productDto1 = productService.saveProduct(new ProductRequest(user1.getEmail(), "Product1", new BigDecimal(100)));
        ProductDto productDto2 = productService.saveProduct(new ProductRequest(user1.getEmail(), "Product2", new BigDecimal(50)));
        ProductDto productDto3 = productService.saveProduct(new ProductRequest(user1.getEmail(), "Product3", new BigDecimal(75)));
        ProductDto productDto4 = productService.saveProduct(new ProductRequest(user2.getEmail(), "Product4", new BigDecimal(20)));
        ProductDto productDto5 = productService.saveProduct(new ProductRequest(user2.getEmail(), "Product5", new BigDecimal(40)));
        ProductDto productDto6 = productService.saveProduct(new ProductRequest(user2.getEmail(), "Product6", new BigDecimal(60)));
        ProductDto productDto7 = productService.saveProduct(new ProductRequest(user3.getEmail(), "Product7", new BigDecimal(85)));
        ProductDto productDto8 = productService.saveProduct(new ProductRequest(user3.getEmail(), "Product8", new BigDecimal(105)));
        ProductDto productDto9 = productService.saveProduct(new ProductRequest(user3.getEmail(), "Product9", new BigDecimal(45)));

        List<InvoiceRequest> invoiceRequestList1 = Arrays.asList(
                new InvoiceRequest("Semih", "Erkaraca", "semiherkaraca@windowslive.com", new BigDecimal(80), productDto1.getName(), "TR000"),
                new InvoiceRequest("Semih", "Erkaraca", "semiherkaraca@windowslive.com", new BigDecimal(100), productDto2.getName(), "TR001"),
                new InvoiceRequest("Semih", "Erkaraca", "semiherkaraca@windowslive.com", new BigDecimal(140), productDto3.getName(), "TR002")
        );

        List<InvoiceRequest> invoiceRequestList2 = Arrays.asList(
                new InvoiceRequest("Ali", "Veli", "aliveli@gmail.com", new BigDecimal(20), productDto4.getName(), "TR003"),
                new InvoiceRequest("Ali", "Veli", "aliveli@gmail.com", new BigDecimal(100), productDto5.getName(), "TR004"),
                new InvoiceRequest("Ali", "Veli", "aliveli@gmail.com", new BigDecimal(60), productDto6.getName(), "TR005")
        );

        List<InvoiceRequest> invoiceRequestList3 = Arrays.asList(
                new InvoiceRequest("Kemal", "Kırmızı", "kemalkirmizi@gmail.com", new BigDecimal(80), productDto7.getName(), "TR006"),
                new InvoiceRequest("Kemal", "Kırmızı", "kemalkirmizi@gmail.com", new BigDecimal(100), productDto8.getName(), "TR007"),
                new InvoiceRequest("Kemal", "Kırmızı", "kemalkirmizi@gmail.com", new BigDecimal(140), productDto9.getName(), "TR008")
        );

        List<String> resultList1 = invoiceService.saveInvoice(new MultipleInvoiceRequest(invoiceRequestList1));
        List<String> resultList2 = invoiceService.saveInvoice(new MultipleInvoiceRequest(invoiceRequestList2));
        List<String> resultList3 = invoiceService.saveInvoice(new MultipleInvoiceRequest(invoiceRequestList3));

        System.out.println();
        resultList1.forEach(System.out::println);
        System.out.println("------------------------------------");
        resultList2.forEach(System.out::println);
        System.out.println("------------------------------------");
        resultList3.forEach(System.out::println);
        System.out.println("------------------------------------");

        System.out.println(invoiceService.getReport());
    }*/
}
