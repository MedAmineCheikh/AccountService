package com.accountsservices.Service;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.dto.AppUserRequestDTO;
import com.accountsservices.dto.AppUserResponseDTO;
import com.accountsservices.dto.AppUserUpdateDTO;

import java.util.List;

public interface AccountService {

    public AppUserResponseDTO addUser(AppUserRequestDTO userRequestDTO);
    AppRole addRole(AppRole role);
    void addRoleToUser(String roleName,String username);
    AppUser LoadUserByUsername(String username);
    List<AppUserResponseDTO>listUsers();

    public void updateUser(AppUserUpdateDTO dto);
}
