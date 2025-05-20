package com.user.imvs.controller;

import com.user.imvs.dtos.SupplierDTO;
import com.user.imvs.model.Supplier;
import com.user.imvs.service.ISupplierService;
import com.user.imvs.service.SupplierServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("v1/suppliers")
public class SupplierController {
    private ISupplierService supplierService;
    public SupplierController(SupplierServiceImpl supplierService) {
        this.supplierService = supplierService;
    }
    @PostMapping
    public ResponseEntity<SupplierDTO> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createSupplier(supplier));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("{/id}")
    public ResponseEntity<SupplierDTO> getSupplierById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PutMapping("{/id}")
    public ResponseEntity<SupplierDTO> updateSupplier(@PathVariable("id")Long id,@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.updateSupplier(id,supplier));
    }
    @DeleteMapping("{/id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable("id") Long id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}
