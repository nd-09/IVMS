package com.user.imvs.service;

import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.model.Product;

import java.util.List;

public interface IProduct {
    ProductDTO createProduct(Product product);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
