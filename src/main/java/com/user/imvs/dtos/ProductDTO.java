package com.user.imvs.dtos;

import lombok.Data;

@Data
public class ProductDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private CategoryDTO category;
    private SupplierDTO supplier;
}
