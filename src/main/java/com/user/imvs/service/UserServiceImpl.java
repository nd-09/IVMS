package com.user.imvs.service;

import com.user.imvs.dtos.UserDTO;
import com.user.imvs.exception.ResourceNotFound;
import com.user.imvs.mappers.UserMapper;
import com.user.imvs.model.User;
import com.user.imvs.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
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
        String encodedPassword = "{noop}password"; // placeholder
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

