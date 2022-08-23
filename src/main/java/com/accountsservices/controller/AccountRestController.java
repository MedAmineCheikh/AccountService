package com.accountsservices.controller;

import com.accountsservices.Entity.AppRole;
import com.accountsservices.Entity.AppUser;
import com.accountsservices.Service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }
    @PostMapping("/adduser")
   public AppUser addUser(@RequestBody AppUser appUser)
    {
        return accountService.addUser(appUser);
    }
    @PostMapping("/addrole")
   public AppRole addRole(@RequestBody AppRole role)
    {
        return accountService.addRole(role);
    }
    @PutMapping("/addRoleToUser/{roleName}/{username}")
  public   void addRoleToUser(@PathVariable String roleName,@PathVariable String username)
    {
        accountService.addRoleToUser(roleName,username);
    }
    @GetMapping("/user/{username}")
  public   AppUser LoadUserByUsername(@PathVariable String username)
    {
        return null;
    }
    @GetMapping("/users")
   public List<AppUser> listUsers()
    {
        return accountService.listUsers();
    }
}
