package com.wholesale.demo.controller;

import com.wholesale.demo.dto.CustomerDTO;
import com.wholesale.demo.dto.SupplierDTO;
import com.wholesale.demo.service.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/supplier")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    //save
    @PostMapping("/save")
    public ResponseEntity<SupplierDTO> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        SupplierDTO savedSupplier = supplierService.saveSupplier(supplierDTO);
        return ResponseEntity.ok(savedSupplier);
    }
    //get all
    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        List<SupplierDTO> suppliers = supplierService.getAllSuppliers();
        return ResponseEntity.ok(suppliers);
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable Long id) {
        SupplierDTO receivedSupplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(receivedSupplier);
    }

    //delete
    @DeleteMapping("/delete/{id}")
    public String deleteSupplier(@PathVariable Long id) {
        supplierService.deleteSupplierById(id);
        return "Deleted Supplier with id " + id;
    }

    //update supplier
    @PutMapping("/update/{id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable Long id, @RequestBody SupplierDTO supplierDTO) {
        SupplierDTO updatedSupplier =  supplierService.updateSupplier(id, supplierDTO);
        return ResponseEntity.ok(updatedSupplier);
    }

    //search customer from any field values
    @GetMapping("/search")
    public ResponseEntity<List<SupplierDTO>> searchSuppliers(@RequestParam String searchKeyword) {
        List<SupplierDTO> matchingSuppliers = supplierService.searchSuppliers(searchKeyword);
        return ResponseEntity.ok(matchingSuppliers);
    }
}
