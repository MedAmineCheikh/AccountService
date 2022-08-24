package com.accountsservices.Service;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.Entity.Employe;
import com.accountsservices.Entity.Statut;
import com.accountsservices.Exceptions.EmployeNotFoundException;
import com.accountsservices.Exceptions.StatutNotActiveException;
import com.accountsservices.dto.AppUserRequestDTO;
import com.accountsservices.dto.AppUserResponseDTO;
import com.accountsservices.dto.AppUserUpdateDTO;
import com.accountsservices.mappers.AppUserMapper;
import com.accountsservices.openFeign.EmployeRestController;
import com.accountsservices.repo.AppRoleRepo;
import com.accountsservices.repo.AppUserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {

    private AppUserMapper appUserMapper;
    private AppUserRepo appUserRepo;
    private AppRoleRepo appRoleRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private EmployeRestController employeRestController;

    public AccountServiceImpl(AppUserMapper appUserMapper, AppUserRepo appUserRepo, AppRoleRepo appRoleRepo, BCryptPasswordEncoder bCryptPasswordEncoder, EmployeRestController employeRestController) {
        this.appUserMapper = appUserMapper;
        this.appUserRepo = appUserRepo;
        this.appRoleRepo = appRoleRepo;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.employeRestController = employeRestController;
    }


    @Override
    public AppUserResponseDTO addUser(AppUserRequestDTO userRequestDTO) {
        try {
            Employe employe = employeRestController.getEmploye(userRequestDTO.getEmployeId());

        } catch (Exception e) {
            throw new EmployeNotFoundException("Employe Not Found");
        }
        try {
            Employe employe = employeRestController.getEmploye(userRequestDTO.getEmployeId());
            if (employe.getStatut().equals(Statut.Desactiver)) {
                throw new StatutNotActiveException("This Employe is Desactivated");
            }
        } catch (Exception s) {
            throw s;
        }
        AppUser user = appUserRepo.findByUsername(userRequestDTO.getUsername());
        if (user != null) throw new RuntimeException("User already exists !");
        if (!userRequestDTO.getPassword().equals(userRequestDTO.getConfirmedpassword()))
            throw new RuntimeException("Please confirm your password");
        userRequestDTO.setPassword(bCryptPasswordEncoder.encode(userRequestDTO.getPassword()));

        AppUser appUser = appUserMapper.AppUserRequestDTOAppUser(userRequestDTO);
        appUser.setUpdatedAt(new Date());
        appUserRepo.save(appUser);
        addRoleToUser("EMPLOYE", appUser.getUsername());
        AppUserResponseDTO appUserResponseDTO = appUserMapper.AppUserTOAppUserResponseDTO(appUser);
        return appUserResponseDTO;


    }

    @Override
    public AppRole addRole(AppRole role) {
        String up = role.getRoleName().toUpperCase();
        role.setRoleName(up);
        AppRole appRole = appRoleRepo.findByRoleName(up);
        if (appRole == null) {
            return appRoleRepo.save(role);
        } else throw new RuntimeException("Role already exist");
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        String up = roleName.toUpperCase();
        AppUser appUser = appUserRepo.findByUsername(username);
        AppRole appRole = appRoleRepo.findByRoleName(up);
        int x = 0;
        for (AppRole role : appUser.getAppRoles()) {
            if (role.getRoleName().equals(appRole.getRoleName())) {
                x++;
            }
        }
        if (x == 0) {
            appUser.getAppRoles().add(appRole);
        }

    }

    @Override
    public AppUser LoadUserByUsername(String username) {
        AppUser user = appUserRepo.findByUsername(username);
        System.out.println(user);
        return user;
    }

    @Override
    public List<AppUserResponseDTO> listUsers() {
        List<AppUser> users = appUserRepo.findAll();
        for (AppUser appUser:users){
            Employe employe= employeRestController.getEmploye(appUser.getEmployeId());
                appUser.setEmploye(employe);
        }
        List<AppUserResponseDTO> appUserResponseDTOS= users.stream()
                .map(us-> appUserMapper.AppUserTOAppUserResponseDTO(us))
                .collect(Collectors.toList());
        return appUserResponseDTOS;
    }

    @Override
    public void updateUser(AppUserUpdateDTO dto) {
        AppUser user = appUserRepo.findById(dto.getId()).get();
        if (!user.getEmployeId().equals(null)) {
            try {
                Employe employe = employeRestController.getEmploye(dto.getEmployeId());

            } catch (Exception e) {
                throw new EmployeNotFoundException("Employe Not Found");
            }
        }
        user.setUpdatedAt(new Date());
        appUserMapper.updateAppUserFromDto(dto, user);
    }




    }

