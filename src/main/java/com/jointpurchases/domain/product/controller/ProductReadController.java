package com.jointpurchases.domain.product.controller;

import com.jointpurchases.domain.product.service.ProductReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductReadController {

    private final ProductReadService productReadService;


    @GetMapping("/products")
    public ResponseEntity<?> getAllProduct(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size)
    {
        return ResponseEntity.ok()
                .body(productReadService.getAllProduct(PageRequest.of(page - 1, size)));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProduct(@PathVariable final Long id)
    {
        return ResponseEntity.ok()
                .body(productReadService.getProduct(id));
    }

    @GetMapping("/product/search")
    public ResponseEntity<?> getSearchProduct(
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "c", required = false) String category,
            @RequestParam(name = "q") String query,
            @RequestParam(name = "sort", required = false) String sort)
    {
        return ResponseEntity.ok()
                .body(productReadService.getSearchProduct(PageRequest.of(page - 1, size), category, query, sort));
    }

}