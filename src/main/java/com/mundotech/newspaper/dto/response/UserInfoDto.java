package com.mundotech.newspaper.dto.response;

import java.util.Set;

public record UserInfoDto(
    Integer id,
    String name,
    String email,
    Set<RoleInfoDto> roles
) {}
