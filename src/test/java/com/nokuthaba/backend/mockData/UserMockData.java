package com.nokuthaba.backend.mockData;

import com.nokuthaba.backend.models.Role;
import com.nokuthaba.backend.models.User;

import java.util.Collections;

public class UserMockData {

    public static User getCompleteUserMock(){
        return User.builder()
                .userName("Noku")
                .userFirstName("Nokuthaba")
                .userLastName("Lunga")
                .userPassword("noku@1998")
                .role(Collections.singleton(getUserRoleMock()))
                .build();
    }

    public static Role getUserRoleMock(){
        return Role.builder()
                .roleDescription("Administrator")
                .roleName("ADMIN")
                .build();
    }
}
