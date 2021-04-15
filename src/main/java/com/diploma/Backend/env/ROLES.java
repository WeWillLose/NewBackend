package com.diploma.Backend.env;

import com.diploma.Backend.model.ERole;
import com.diploma.Backend.model.Role;

public final class ROLES {
    private ROLES(){}
    public static Role ADMIN = new Role(ERole.ADMIN);
    public static Role TEACHER = new Role(ERole.TEACHER);
    public static Role CHAIRMAN = new Role(ERole.CHAIRMAN);
}
