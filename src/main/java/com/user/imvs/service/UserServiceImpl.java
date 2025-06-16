package com.user.imvs.service;

import com.user.imvs.dtos.LoginRequestDTO;
import com.user.imvs.dtos.RegisterRequestDTO;
import com.user.imvs.dtos.UserDTO;
import com.user.imvs.exception.ResourceNotFound;
import com.user.imvs.helper.AuthProxyClient;
import com.user.imvs.mappers.UserMapper;
import com.user.imvs.model.User;
import com.user.imvs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthProxyClient authProxyClient;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper,PasswordEncoder passwordEncoder,AuthProxyClient authProxyClient) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.authProxyClient = authProxyClient;
    }

    @Override
    public List<UserDTO> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public UserDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound("User not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDTO create(UserDTO dto) {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setUsername(dto.getUsername());
        requestDTO.setPassword(dto.getPassword());
        try{
            authProxyClient.registerUser(requestDTO);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to register user in auth-service: " + e.getMessage());
        }

        String encodedPassword =passwordEncoder.encode(dto.getPassword());
        User user = userMapper.toEntity(dto, encodedPassword);
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("User not found");
        }
        userRepository.deleteById(id);
    }
}

