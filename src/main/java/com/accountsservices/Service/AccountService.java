package com.accountsservices.Service;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;

import java.util.List;

public interface AccountService {

    AppUser addUser(AppUser appUser);
    AppRole addRole(AppRole role);
    void addRoleToUser(String roleName,String username);
    AppUser LoadUserByUsername(String username);
    List<AppUser> listUsers();
}
