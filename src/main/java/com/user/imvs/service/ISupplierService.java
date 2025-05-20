package com.user.imvs.service;

import com.user.imvs.dtos.SupplierDTO;
import com.user.imvs.model.Supplier;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ISupplierService {
    SupplierDTO createSupplier(Supplier supplier);
    SupplierDTO updateSupplier(Long id,Supplier supplier);
    List<SupplierDTO> getAllSuppliers();
    SupplierDTO getSupplierById(Long id);
    void deleteSupplier(Long id);
}
