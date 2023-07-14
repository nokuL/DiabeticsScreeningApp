package com.nokuthaba.backend.unit_tests;

import com.nokuthaba.backend.mockData.UserMockData;
import com.nokuthaba.backend.models.Role;
import com.nokuthaba.backend.models.User;
import com.nokuthaba.backend.repository.RoleRepository;
import com.nokuthaba.backend.repository.UserRepository;
import com.nokuthaba.backend.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    private Role role;

    private User user;

    private String encodedPassword;

    @BeforeEach
     void setUp() {
        role = UserMockData.getUserRoleMock();

        user = UserMockData.getCompleteUserMock();

    }


    @Test
    public void testInitRole() {

        when(roleRepository.save(role)).thenReturn(role);

        when(passwordEncoder.encode("noku@987")).thenReturn(encodedPassword);

        when(userRepository.save(user)).thenReturn(user);

        userService.initRoleAndUser();

        verify(roleRepository, times(2)).save(any(Role.class));
        verify(userRepository, times(2)).save(any(User.class));

    }

  /*  @Test
    public void testValidNewUserRegistration() {

        User user = UserMockData.getCompleteUserMock();

        Role role = UserMockData.getUserRoleMock();
        when(roleRepository.findById("User")).thenReturn(Optional.of(role));

        when(userRepository.save(any(User.class))).thenReturn(user);

        User savedUser = userService.registerNewUser(user);

        assertEquals(user, savedUser);
        assertEquals(1, savedUser.getRole().size());
        verify(roleRepository).findById("User");
        verify(userRepository).save(user);


    }*/

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidNewUserReg(){
        user = UserMockData.getCompleteUserMock();

        when(roleRepository.findById("ADMIN")).thenThrow(new IllegalArgumentException("Invalid user role"));

        when(userRepository.save(user)).thenReturn(user);

        userService.registerNewUser(user);

        verify(userRepository, times(1)).save(any(User.class));


    }


}
