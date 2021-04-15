package com.diploma.Backend.rest.dto;

import com.diploma.Backend.model.ERole;
import com.diploma.Backend.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    @NonNull
    private ERole name;

    public Role toRole(){
        return name==null?null:new Role(name);
    }
}
