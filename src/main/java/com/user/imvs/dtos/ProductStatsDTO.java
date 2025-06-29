package com.user.imvs.dtos;

import java.util.List;

public class ProductStatsDTO {
    private Long totalProducts;
    private ProductDTO recentlyAdded;
    private Double totalInventoryValuation;
    private List<ProductDTO> lowStockProducts;

    public Long getTotalProducts() {
        return this.totalProducts;
    }
    public void setTotalProducts(Long totalProducts) {
        this.totalProducts = totalProducts;
    }
    public ProductDTO getRecentlyAdded() {
        return this.recentlyAdded;
    }
    public void setRecentlyAdded(ProductDTO recentlyAdded) {
        this.recentlyAdded = recentlyAdded;
    }
    public Double getTotalInventoryValuation() {
        return this.totalInventoryValuation;
    }
    public void setTotalInventoryValuation(Double totalInventoryValuation) {
        this.totalInventoryValuation = totalInventoryValuation;
    }
    public List<ProductDTO> getLowStockProducts() {
        return this.lowStockProducts;
    }
    public void setLowStockProducts(List<ProductDTO> lowStockProducts) {
        this.lowStockProducts = lowStockProducts;
    }
}
