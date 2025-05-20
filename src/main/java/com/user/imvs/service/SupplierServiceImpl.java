package com.user.imvs.service;

import com.user.imvs.dtos.SupplierDTO;
import com.user.imvs.exception.SupplierNotFoundException;
import com.user.imvs.model.Supplier;
import com.user.imvs.repository.SupplierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements ISupplierService {

    private SupplierRepository supplierRepository;
    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public SupplierDTO createSupplier(Supplier supplier) {
        Supplier s=supplierRepository.save(supplier);
        return toDTO(s);
    }

    @Override
    public SupplierDTO updateSupplier(Long id, Supplier supplier) {
    return null;
    }

    @Override
    public List<SupplierDTO> getAllSuppliers() {
        List<Supplier> s= supplierRepository.findAll();
        List<SupplierDTO> dtos= new ArrayList<>();
        for(Supplier suplier:s){
            SupplierDTO supDto=toDTO(suplier);
            dtos.add(supDto);
        }
        return dtos;
    }

    @Override
    public SupplierDTO getSupplierById(Long id) {
        Optional<Supplier> sup= supplierRepository.findById(id);
        if(sup.isPresent()){
            return toDTO(sup.get());
        }else{
            throw new SupplierNotFoundException("Supplier not found");
        }
    }

    @Override
    public void deleteSupplier(Long id) {
        if(supplierRepository.existsById(id)) {
            supplierRepository.deleteById(id);
        }else{
            throw new SupplierNotFoundException("Supplier not found");
        }
    }
    private SupplierDTO toDTO(Supplier supplier) {
    SupplierDTO supplierDTO = new SupplierDTO();
    supplierDTO.setName(supplier.getCompanyName());
    return supplierDTO;
    }
}
