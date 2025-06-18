package com.user.imvs.mappers;

import com.user.imvs.dtos.RegisterResponseDTO;
import com.user.imvs.dtos.UserDTO;
import com.user.imvs.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    public User toEntity(UserDTO dto, String encodedPassword) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(dto.getRole());
        return user;
    }
    public RegisterResponseDTO toRegisterResponseDto(User user, String encodedPassword,String token) {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setUsername(user.getUsername());
        registerResponseDTO.setEmail(user.getEmail());
        registerResponseDTO.setPassword(encodedPassword);
        registerResponseDTO.setToken(token);
        registerResponseDTO.setRole(user.getRole());
        registerResponseDTO.setId(user.getId());

        return registerResponseDTO;
    }
}

