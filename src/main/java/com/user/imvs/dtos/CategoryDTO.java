package com.user.imvs.dtos;

import lombok.Data;

@Data
public class CategoryDTO {
    private String name;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public CategoryDTO(String name){
        this.name = name;
    }
    public CategoryDTO(){}
}
