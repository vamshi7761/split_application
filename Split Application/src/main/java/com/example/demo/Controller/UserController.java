package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;
import com.example.demo.Entity.User;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	public Optional<User> getUserById(@PathVariable Long id) {
		return userService.getUserById(id);
	}

	@GetMapping("/group/{groupId}")
	public Set<Long> getUserIdsByGroupId(@PathVariable Long groupId) {
		return userService.getUserIdsByGroupId(groupId);
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userService.saveUser(user);
	}

	@PutMapping("/{id}")
	public User updateUser(@PathVariable Long id, @RequestBody User user) {
		user.setUserId(id);
		return userService.saveUser(user);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
}