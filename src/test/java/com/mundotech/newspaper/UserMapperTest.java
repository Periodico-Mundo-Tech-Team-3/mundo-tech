package com.mundotech.newspaper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mundotech.newspaper.mapper.UserMapper;
import com.mundotech.newspaper.dto.response.UserInfoDto;
import com.mundotech.newspaper.entity.Role;
import com.mundotech.newspaper.entity.User;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    private final UserMapper mapper = new UserMapper();

    @Test
    void toUserInfoDto_mapsFields() {
        Role role = new Role();
        role.setId(1); role.setName("author");

        User user = new User();
        user.setId(5); user.setName("Ana"); user.setEmail("a@b.com");
        user.setRoles(Set.of(role));

        UserInfoDto dto = mapper.toUserInfoDto(user);

        assertEquals(5, dto.id());
        assertEquals("Ana", dto.name());
        assertEquals("a@b.com", dto.email());
        assertEquals(1, dto.roles().size());
        assertTrue(dto.roles().stream()
                      .anyMatch(r -> r.name().equals("author")));
    }

    @Test
    void toUserInfoDto_nullUser_returnsNull() {
        assertNull(mapper.toUserInfoDto(null));
    }
}