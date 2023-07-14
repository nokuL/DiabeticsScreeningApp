package com.nokuthaba.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.nokuthaba.backend.repository.RoleRepository;
import com.nokuthaba.backend.repository.UserRepository;
import com.nokuthaba.backend.models.Role;
import com.nokuthaba.backend.models.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void initRoleAndUser() {

        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("Admin role");
        roleRepository.save(adminRole);

        Role clientRole = new Role();
        clientRole.setRoleName("Client");
        clientRole.setRoleDescription("Default role for newly created record");
        roleRepository.save(clientRole);

        User adminUser = new User();
        adminUser.setUserName("admin123");
        adminUser.setUserPassword(getEncodedPassword("admin@pass"));
        adminUser.setUserFirstName("admin");
        adminUser.setUserLastName("admin");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(adminRole);
        adminUser.setRole(adminRoles);
        userRepository.save(adminUser);

      User user = new User();
      user.setUserName("user");
      user.setUserPassword(getEncodedPassword("user@123"));
       user.setUserFirstName("user");
      user.setUserLastName("user");
       Set<Role> userRoles = new HashSet<>();
      userRoles.add(clientRole);
      user.setRole(userRoles);
       userRepository.save(user);
    }

    public User registerNewUser(User user) {
        Role role = roleRepository.findById("Admin").orElseThrow(()-> new IllegalArgumentException("Invalid user role"));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(role);
        user.setRole(userRoles);
        user.setUserPassword(getEncodedPassword(user.getUserPassword()));

        return userRepository.save(user);
    }

    public String getEncodedPassword(String password) {
        return passwordEncoder.encode(password);
    }
}
