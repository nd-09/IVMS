package com.user.imvs.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

}
