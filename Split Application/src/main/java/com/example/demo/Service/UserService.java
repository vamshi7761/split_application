package com.example.demo.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmailServic e;

    /**
     * Retrieve all users.
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Find a user by ID.
     *
     * @param userId ID of the user
     * @return Optional containing the user if found
     */
    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Find all user IDs belonging to a specific group.
     *
     * @param groupId ID of the group
     * @return Set of user IDs in the group
     */
    public Set<Long> getUserIdsByGroupId(Long groupId) {
        return userRepository.findUserIdsByGroupId(groupId);
    }

    /**
     * Create or update a user.
     *
     * @param user User to save
     * @return Saved user entity
     */
    public User saveUser(User user) {
    	e.verifyEmailAddress(user.getEmail());
        return userRepository.save(user);
    }

    /**
     * Delete a user by ID.
     *
     * @param userId ID of the user to delete
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}