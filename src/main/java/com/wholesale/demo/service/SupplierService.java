package com.wholesale.demo.service;

import com.wholesale.demo.dto.SupplierDTO;
import com.wholesale.demo.exception.SupplierNotFoundException;
import com.wholesale.demo.mapper.SupplierMapper;
import com.wholesale.demo.model.Supplier;
import com.wholesale.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Transactional
    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        Supplier supplier = supplierMapper.toEntity(supplierDTO);
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toDTO(savedSupplier);
    }

    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toDTO)
                .collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        return supplier.map(supplierMapper::toDTO)
                .orElseThrow(() -> new SupplierNotFoundException("No supplier found with id: " + id));
    }

    @Transactional
    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            Supplier supplierToUpdate = supplierMapper.toEntity(supplierDTO);
            supplierToUpdate.setId(id);
            Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
            return supplierMapper.toDTO(updatedSupplier);
        }
       throw new SupplierNotFoundException("No supplier found with id: " + id);
    }

    @Transactional
    public void deleteSupplier(Long id) {
        Optional<Supplier> supplier = supplierRepository.findById(id);
        if (supplier.isPresent()) {
            supplierRepository.deleteById(id);
        }
        else {
            throw new SupplierNotFoundException("No supplier found with id: " + id);
        }
    }
}