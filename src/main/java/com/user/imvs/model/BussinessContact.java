package com.user.imvs.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public abstract class BussinessContact extends BaseEntity {
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private String contactPerson;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String gstNumber;
}
