package com.user.imvs.service;
import com.user.imvs.dtos.AuthServiceResponse;
import com.user.imvs.dtos.RegisterRequestDTO;
import com.user.imvs.dtos.RegisterResponseDTO;
import com.user.imvs.dtos.UserDTO;
import com.user.imvs.exception.ResourceNotFound;
import com.user.imvs.helper.AuthProxyClient;
import com.user.imvs.mappers.UserMapper;
import com.user.imvs.model.User;
import com.user.imvs.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public RegisterResponseDTO create(UserDTO dto) {
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setUsername(dto.getUsername());
        requestDTO.setPassword(dto.getPassword());
        requestDTO.setRole(dto.getRole().toString().toUpperCase());
        AuthServiceResponse res;

        String encodedPassword =passwordEncoder.encode(dto.getPassword());
        System.out.println("BEFORE save: " + dto.getRole());
        User user = userMapper.toEntity(dto, encodedPassword);
        User saved = userRepository.save(user);

        try{
            System.out.println("Inside try block to call proxy auth");
            res=authProxyClient.registerUser(requestDTO);
            System.out.println("Reaching here after Proxy being called?");
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("Failed to register user in auth-service: " + e.getMessage());
        }

        return userMapper.toRegisterResponseDto(saved,encodedPassword, res.getToken());
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFound("User not found");
        }
        userRepository.deleteById(id);
    }
}

