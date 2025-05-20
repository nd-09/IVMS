package com.user.imvs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Supplier extends BussinessContact {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
}
