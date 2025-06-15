package com.user.imvs.service;

import com.user.imvs.dtos.CategoryDTO;
import com.user.imvs.dtos.ProductDTO;
import com.user.imvs.exception.ResourceNotFound;
import com.user.imvs.mappers.ProductMapper;
import com.user.imvs.model.Product;
import com.user.imvs.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepo;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepo, ProductMapper productMapper) {
        this.productRepo = productRepo;
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

    public ProductDTO createProduct(ProductDTO dto) {
        Product product = productMapper.toEntity(dto);
        Product saved = productRepo.save(product);
        return productMapper.toDto(saved);
    }

    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id))
            throw new ResourceNotFound("Product not found");
        productRepo.deleteById(id);
    }
}
