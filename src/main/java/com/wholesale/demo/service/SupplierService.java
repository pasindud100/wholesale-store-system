package com.wholesale.demo.service;

import com.wholesale.demo.dto.SupplierDTO;
import com.wholesale.demo.mapper.SupplierMapper;
import com.wholesale.demo.model.Supplier;
import com.wholesale.demo.repository.SupplierRepository;
import org.springframework.stereotype.Service;

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

    public SupplierDTO saveSupplier(SupplierDTO supplierDTO) {
        Supplier savedSupplier = supplierMapper.toSupplier(supplierDTO);
        supplierRepository.save(savedSupplier);
        return supplierMapper.toSupplierDTO(savedSupplier);
    }


    public List<SupplierDTO> getAllSuppliers() {
        return supplierRepository.findAll().
                stream().map(supplierMapper::toSupplierDTO).collect(Collectors.toList());
    }

    public SupplierDTO getSupplierById(Long id) {
        return supplierMapper.toSupplierDTO(supplierRepository.findById(id).orElse(null));
    }

    public void deleteSupplierById(Long id) {
       supplierRepository.deleteById(id);
    }

    public SupplierDTO updateSupplier(Long id, SupplierDTO supplierDTO) {
        Optional<Supplier> supplier = supplierRepository.findById(id);

        if (supplier.isPresent()) {
            Supplier supplierToUpdate = supplierMapper.toSupplier(supplierDTO);
            supplierToUpdate.setId(id);
            Supplier updatedSupplier = supplierRepository.save(supplierToUpdate);
            return supplierMapper.toSupplierDTO(updatedSupplier);
        }
        return null;
    }

    public List<SupplierDTO> searchSuppliers(String searchKeyword) {
        List<Supplier> suppliers = supplierRepository.searchSuppliers(searchKeyword);
        return suppliers.stream()
                .map(supplierMapper::toSupplierDTO)
                .collect(Collectors.toList());
    }

}
