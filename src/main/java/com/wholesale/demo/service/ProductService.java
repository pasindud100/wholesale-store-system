package com.wholesale.demo.service;

import com.wholesale.demo.dto.ProductDTO;
import com.wholesale.demo.exception.ProductNotFoundException;
import com.wholesale.demo.mapper.ProductMapper;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(productMapper ::toDTO)
                .orElseThrow(()-> new ProductNotFoundException("Searched product not found with id: " + id));
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            Product productToUpdate = productMapper.toEntity(productDTO);
            productToUpdate.setId(id);
            Product updatedProduct = productRepository.save(productToUpdate);
            return productMapper.toDTO(updatedProduct);
        }
        throw new ProductNotFoundException("Searched product not found with id: " + id);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Optional<Product> product = productRepository.findById(id);
       if(product.isPresent()) {
           productRepository.delete(product.get());
       }
       else{
           throw new ProductNotFoundException("Searched product not found with id: " + id);
       }
    }
}