package com.user.imvs.service;

import com.user.imvs.dtos.UserDTO;

import java.util.List;

public interface IUserService {

        List<UserDTO> getAll();
        UserDTO getById(Long id);
        UserDTO create(UserDTO dto);
        void delete(Long id);

}
