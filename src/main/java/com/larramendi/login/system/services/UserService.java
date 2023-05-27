package com.larramendi.login.system.services;

import com.larramendi.login.system.dto.UserDTO;
import com.larramendi.login.system.entities.User;
import com.larramendi.login.system.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        List<User> savedListUsers = userRepository.findAll();
        return savedListUsers.stream().map(UserDTO::new).toList();
    }

    public UserDTO getUserById(Long id) {
        User savedUser = userRepository.findById(id).get();
        return new UserDTO(savedUser);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = mapDtoToUser(userDTO);
        userRepository.save(user);
        return new UserDTO(user);
    }

    public UserDTO updateUser(UserDTO userDTO, Long id) {
        User existentUser = userRepository.findById(id).get();
        existentUser.setName(userDTO.getName());
        existentUser.setBirth(userDTO.getBirth());
        existentUser.setEmail(userDTO.getEmail());
        existentUser.setPassword(userDTO.getPassword());
        userRepository.save(existentUser);

        return new UserDTO(existentUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private User mapDtoToUser(UserDTO userDTO) {
        return new User(
                userDTO.getId(),
                userDTO.getName(),
                userDTO.getBirth(),
                userDTO.getEmail(),
                userDTO.getPassword());
    }
}
