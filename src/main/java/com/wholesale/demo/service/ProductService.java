package com.wholesale.demo.service;

import com.wholesale.demo.dto.ProductDTO;
import com.wholesale.demo.exception.CategoryNotFoundException;
import com.wholesale.demo.exception.ProductNotFoundException;
import com.wholesale.demo.exception.SupplierNotFoundException;
import com.wholesale.demo.mapper.ProductMapper;
import com.wholesale.demo.model.Category;
import com.wholesale.demo.model.Product;
import com.wholesale.demo.model.Supplier;
import com.wholesale.demo.repository.CategoryRepository;
import com.wholesale.demo.repository.ProductRepository;
import com.wholesale.demo.repository.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Category categoryExisting = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(()->new CategoryNotFoundException("Category not found with id "+productDTO.getCategoryId()));

        Supplier supplierExisting = supplierRepository.findById(productDTO.getSupplierId())
                .orElseThrow(()->new SupplierNotFoundException("Supplier not found with id "+productDTO.getSupplierId()));

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
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .orElseThrow(() -> new ProductNotFoundException("Searched product not found with id: " + id));
    }

    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(()->new ProductNotFoundException("Searched product not found with id: " + id));
        Product productToUpdate = productMapper.toEntity(productDTO);
        productToUpdate.setId(id);
        Product updatedProduct = productRepository.save(productToUpdate);
        return productMapper.toDTO(updatedProduct);
    }

    @Transactional
    public void deleteProduct(Long id) {
        Product productToDelete = productRepository.findById(id).
                orElseThrow(() -> new ProductNotFoundException("Searched product not found with id: " + id +". Then you cant delete."));

        productRepository.delete(productToDelete);
    }
}