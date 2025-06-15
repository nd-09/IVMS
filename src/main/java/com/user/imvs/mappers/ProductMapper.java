package com.user.imvs.mappers;

import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setPrice(product.getPrice());
        return dto;
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setStockQuantity(dto.getStockQuantity());
        product.setPrice(dto.getPrice());
        return product;
    }
}
