package com.user.imvs.service;

import com.user.imvs.dtos.CategoryDTO;
import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.dtos.SupplierDTO;
import com.user.imvs.exception.ProductNotFoundException;
import com.user.imvs.model.Product;
import com.user.imvs.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProduct{

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductDTO createProduct(Product product) {
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> prods= productRepository.findAll();
        List<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product : prods) {
            productDTOS.add(convertToDTO(product));
        }
        return productDTOS;
    }

    @Override
    public ProductDTO updateProduct(Long id, Product updatedProduct) {
        Optional<Product> pro =productRepository.findById(id);
        if(pro.isPresent()) {
            Product product = pro.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            productRepository.save(product);
            return convertToDTO(product);
        }else{
            throw new ProductNotFoundException("Product with "+id+" not found");
        }
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id "+id+ ": not found"));
        productRepository.delete(product);
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        CategoryDTO cdto = new CategoryDTO();
        SupplierDTO sdto = new SupplierDTO();
        cdto.setName(product.getCategory().getName());
        sdto.setName(product.getSupplier().getCompanyName());

        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setCategory(cdto);
        dto.setSupplier(sdto);
        return dto;
    }
}
