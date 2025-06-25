package com.user.imvs.controller;

import com.user.imvs.dtos.ProductCreateDTO;
import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.service.IProductService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN_INVENTORY')")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO){
        ProductDTO resp = productService.updateProduct(id, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN_INVENTORY')")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductCreateDTO dto) throws BadRequestException {
        ProductDTO created = productService.createProduct(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}

