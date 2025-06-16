package com.user.imvs.dtos;

public class ProductCreateDTO {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private CategoryDTO category;

        public String getName() {
            return this.name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getDescription() {
            return this.description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public double getPrice() {
            return this.price;
        }
        public void setPrice(double price) {
            this.price = price;
        }
        public Integer getStockQuantity() {
            return this.stockQuantity;
        }
        public void setStockQuantity(Integer quantity) {
            this.stockQuantity = quantity;
        }
        public CategoryDTO getCategory() {
            return this.category;
        }
        public void setCategory(CategoryDTO category) {
            this.category = category;
        }
}
