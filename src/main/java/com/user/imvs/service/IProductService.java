package com.user.imvs.service;

import com.user.imvs.dtos.ProductCreateDTO;
import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.model.Product;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface IProductService {
    ProductDTO createProduct(ProductCreateDTO product) throws BadRequestException;
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO updateProduct(Long id, ProductDTO product);
    void deleteProduct(Long id);
}
