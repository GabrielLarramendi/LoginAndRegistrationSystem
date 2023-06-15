package com.larramendi.login.system.services;

import com.larramendi.login.system.exceptions.EmailAlreadyExistsException;
import com.larramendi.login.system.exceptions.IdNotFoundException;
import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.entities.User;
import com.larramendi.login.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> savedListUsers = userRepository.findAll();
        return savedListUsers.stream().map(UserDTO::new).toList();
    }

    public UserDTO getUserById(Long id) {
        User savedUser = userRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("Usuario com o Id " + id + " nao encontrado."));
        return new UserDTO(savedUser);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = mapDtoToUser(userDTO);
        User existentUser = findUserByEmail(userDTO.getEmail());
        if (existentUser != null && existentUser.getEmail() != null && !existentUser.getEmail().isEmpty()) {
            throw new EmailAlreadyExistsException("Esse e-mail ja esta cadastrado!");
        }
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User existentUser = userRepository
                .findById(id)
                .orElseThrow(() -> new IdNotFoundException("Usuario com o Id " + id + " nao encontrado."));

        User existentUserByEmail = findUserByEmail(userDTO.getEmail());
        if (existentUserByEmail != null && existentUserByEmail.getEmail() != null && !existentUserByEmail.getEmail().isEmpty()) {
            throw new EmailAlreadyExistsException("Email already exists!");
        }
        existentUser.setName(userDTO.getName());
        existentUser.setEmail(userDTO.getEmail());
        existentUser.setPassword(userDTO.getPassword());
        userRepository.save(existentUser);
        return new UserDTO(existentUser);
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        }
        else {
            throw new IdNotFoundException("Usuario com o Id " + id + " nao encontrado");
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private User mapDtoToUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword());
    }
}
