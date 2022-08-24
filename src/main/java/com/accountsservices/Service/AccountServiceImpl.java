package com.accountsservices.Service;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.repo.AppRoleRepo;
import com.accountsservices.repo.AppUserRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private AppUserRepo appUserRepo;
    private AppRoleRepo appRoleRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AccountServiceImpl(AppUserRepo appUserRepo, AppRoleRepo appRoleRepo, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepo = appUserRepo;
        this.appRoleRepo = appRoleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public AppUser addUser(AppUser appUser) {
        AppUser user=appUserRepo.findByUsername(appUser.getUsername());
        if (user!=null)throw new RuntimeException("User already exists !");
        if (!appUser.getPassword().equals( appUser.getConfirmedpassword())) throw new RuntimeException("Please confirm your password");
        appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
        appUser.setUpdatedAt(new Date());
        appUserRepo.save(appUser);
        addRoleToUser("EMPLOYE",appUser.getUsername());
        return appUser;


    }

    @Override
    public AppRole addRole(AppRole role) {
        String up= role.getRoleName().toUpperCase();
        role.setRoleName(up);
        AppRole appRole=appRoleRepo.findByRoleName(up);
        if (appRole == null){
        return appRoleRepo.save(role);}
        else throw new RuntimeException("Role already exist");
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        String up= roleName.toUpperCase();
        AppUser appUser =appUserRepo.findByUsername(username);
        AppRole appRole=appRoleRepo.findByRoleName(up);
        for (AppRole role: appUser.getAppRoles())
        {
            if (role.getRoleName().equals(appRole.getRoleName()))
            {
              throw new RuntimeException("Role Already exist !");
            }
        }

        appUser.getAppRoles().add(appRole);}




    @Override
    public AppUser LoadUserByUsername(String username) {
        return appUserRepo.findByUsername(username);
    }

    @Override
    public List<AppUser> listUsers() {
        List<AppUser>users =appUserRepo.findAll();
        return users;
    }
}
