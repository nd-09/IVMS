package com.user.imvs.service;

import com.user.imvs.dtos.CategoryDTO;
import com.user.imvs.dtos.ProductCreateDTO;
import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.dtos.ProductStatsDTO;
import com.user.imvs.exception.ResourceNotFound;
import com.user.imvs.mappers.ProductMapper;
import com.user.imvs.model.Category;
import com.user.imvs.model.Product;
import com.user.imvs.repository.CategoryRepository;
import com.user.imvs.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepo, ProductMapper productMapper, CategoryRepository categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo=categoryRepo;
        this.productMapper = productMapper;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepo.findAll().stream()
                .map(productMapper::toDto)
                .toList();
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFound("Product not found"));
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO updatedProduct) {
        Optional<Product> pro =productRepo.findById(id);
        if(pro.isPresent()) {
            CategoryDTO cat=new CategoryDTO();
            cat.setName(updatedProduct.getCategory().getName());
            Product product = pro.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            productRepo.save(product);
            return productMapper.toDto(product);
        }else{
            throw new ResourceNotFound("Product with "+id+" not found");
        }
    }

    @Transactional
    @Override
    public ProductDTO createProduct(ProductCreateDTO dto) {
        try {
            Category category = categoryRepo.findByName(dto.getCategory().getName().toUpperCase())
                    .orElseGet(() -> {
                        Category newCat = new Category();
                        newCat.setName(dto.getCategory().getName().toUpperCase());
                        return categoryRepo.saveAndFlush(newCat);
                    });

            Product product = new Product();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            product.setStockQuantity(dto.getStockQuantity());
            product.setCategory(category);
            productRepo.save(product);

            return productMapper.toDto(product);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product");
        }
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id))
            throw new ResourceNotFound("Product not found");
        productRepo.deleteById(id);
    }

    @Override
    public ProductStatsDTO getProductStats() {
        ProductStatsDTO productStatsDTO = new ProductStatsDTO();
        ProductDTO recentlyAddedProduct = new ProductDTO();
        try{
            productStatsDTO.setTotalProducts(productRepo.count());
            Optional<Product> p= productRepo.findTopByOrderByCreatedAtDesc();
            productStatsDTO.setTotalInventoryValuation(productRepo.getTotalInventoryValuation());
            productStatsDTO.setLowStockProducts(productRepo.findByStockQuantityIsLessThanEqual(5)
                    .stream()
                    .map(productMapper::toDto)
                    .toList());
            if(p.isPresent()) {
                CategoryDTO cat=new CategoryDTO();
                cat.setName(p.get().getName().toUpperCase());
                recentlyAddedProduct.setCategory(cat);
                recentlyAddedProduct.setStockQuantity(p.get().getStockQuantity());
                recentlyAddedProduct.setDescription(p.get().getDescription());
                recentlyAddedProduct.setPrice(p.get().getPrice());
                recentlyAddedProduct.setName(p.get().getName());
            }
        }catch(Exception e){
            System.out.println(e);
        }
        productStatsDTO.setRecentlyAdded(recentlyAddedProduct);
        return productStatsDTO;
    }


}
